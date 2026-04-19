package com.nsane.diesel.projectiles

import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap
import com.hypixel.hytale.component.*
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.util.MathUtil
import com.hypixel.hytale.math.util.TrigMathUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector4d
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect
import com.hypixel.hytale.server.core.entity.EntityUtils
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent
import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent
import com.hypixel.hytale.server.core.inventory.InventoryComponent.Armor
import com.hypixel.hytale.server.core.modules.collision.CharacterCollisionData
import com.hypixel.hytale.server.core.modules.collision.CollisionModule
import com.hypixel.hytale.server.core.modules.collision.CollisionResult
import com.hypixel.hytale.server.core.modules.entity.EntityModule
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.Damage
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCalculatorSystems
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCalculatorSystems.DamageSequence
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.DamageEntityInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageCalculator
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageClass
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageEffects
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.SimulatedTransformComponent
import com.nsane.diesel.flying.SimulatedTransformationSystem
import it.unimi.dsi.fastutil.objects.Object2FloatMap
import it.unimi.dsi.fastutil.objects.ObjectIterator
import java.util.function.DoubleBinaryOperator
import java.util.function.IntFunction
import java.util.stream.IntStream
import javax.annotation.Nonnull
import kotlin.math.max

object DieselProjectileSystem: EntityTickingSystem<EntityStore?>() {
    override fun tick(
        dt: Float,
        idx: Int,
        arch: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        commands: CommandBuffer<EntityStore?>
    ) {
        val spatial = commands.getResource(EntityModule.get().entitySpatialResourceType)
        val projectile = arch.getComponent(idx, DieselProjectileComponent.TYPE) ?: throw IndexOutOfBoundsException()
        val simulated = arch.getComponent(idx, SimulatedTransformComponent.TYPE) ?: throw IndexOutOfBoundsException()
        val boundingBox = arch.getComponent(idx, BoundingBox.getComponentType()) ?: throw IndexOutOfBoundsException()
        val ref = arch.getReferenceTo(idx)
        val pos = SimulatedTransformationSystem.getWorldPosition(commands, simulated)

        val collisions = CollisionResult(true, true)
        val entities = mutableListOf<Ref<EntityStore?>>()
        val velocity = SimulatedTransformationSystem.getWorldVelocity(commands, simulated).scale(dt.toDouble())
        spatial.spatialStructure.collect(pos, 3.0, entities)
        collisions.collisionEntities = entities.mapNotNull { EntityUtils.getEntity(it, commands) }

        CollisionModule.findCollisions(
                boundingBox.boundingBox,
                pos,
            velocity.clone(),
                collisions,
                commands)

        val firstChar = collisions.firstCharacterCollision
        val firstBlock = collisions.firstBlockCollision
        if (firstChar != null) {
            attemptEntityDamage(commands, projectile, ref, firstChar, velocity.clone().normalize())
        } else if (firstBlock != null) {

        } else return


        commands.removeEntity(ref, RemoveReason.REMOVE)
    }

    private fun attemptEntityDamage(
        commandBuffer: CommandBuffer<EntityStore?>,
        projectile: DieselProjectileComponent,
        bullet: Ref<EntityStore?>,
        target: CharacterCollisionData,
        direction: Vector3d
    ) {
        val source = Damage.EnvironmentSource("Bullet")
        val type = DieselProjectileType.ASSET_MAP.getAsset(projectile.type)!!
        val targetRef = target.entityReference
        val yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
        val targetPos = commandBuffer.getComponent(targetRef, TransformComponent.getComponentType())!!.position
        val hit = Vector4d(target.collisionPoint.x, target.collisionPoint.y, target.collisionPoint.z, target.collisionStart)

        val damageCalculator: DamageCalculator? = type.damageCalculator
        val damageEffects: DamageEffects? = type.damageEffects
        val sequentialHits = DamageCalculatorSystems.Sequence() //TODO


        if (damageCalculator != null) {

            val damage = damageCalculator.calculateDamage(0.1)
            if (damage != null && !damage.isEmpty()) {
                val knockbackMultiplier = doubleArrayOf(1.0)
                val armorDamageModifiers = floatArrayOf(0.0f, 1.0f)
                calculateKnockbackAndArmorModifiers(
                    damageCalculator.getDamageClass(),
                    damage,
                    targetRef,
                    bullet,
                    armorDamageModifiers,
                    knockbackMultiplier,
                    commandBuffer
                )

                var knockbackComponent: KnockbackComponent? = null
                if (damageEffects != null && damageEffects.getKnockback() != null) {
                    knockbackComponent = commandBuffer.getComponent<KnockbackComponent?>(
                        targetRef,
                        KnockbackComponent.getComponentType()
                    )
                    if (knockbackComponent == null) {
                        knockbackComponent = KnockbackComponent()
                        commandBuffer.putComponent<KnockbackComponent?>(
                            targetRef,
                            KnockbackComponent.getComponentType(),
                            knockbackComponent
                        )
                    }

                    val knockback = damageEffects.getKnockback()
                    knockbackComponent.velocity = knockback.calculateVector(
                        target.collisionPoint,
                        yaw,
                        targetPos
                    ).scale(knockbackMultiplier[0])
                    knockbackComponent.velocityType = knockback.getVelocityType()
                    knockbackComponent.velocityConfig = knockback.getVelocityConfig()
                    knockbackComponent.duration = knockback.getDuration()
                }

                val hits = DamageCalculatorSystems.queueDamageCalculator(
                    commandBuffer.getExternalData().getWorld(),
                    damage,
                    targetRef,
                    commandBuffer,
                    source,
                    null
                )

                if (hits.size > 0) {
                    val firstDamage = hits[0]
                    val seq = DamageSequence(sequentialHits, damageCalculator)
                    // seq.setEntityStatOnHit(this.entityStatsOnHit)
                    firstDamage.putMetaObject<DamageSequence?>(DamageCalculatorSystems.DAMAGE_SEQUENCE, seq)
                    if (damageEffects != null) {
                        damageEffects.addToDamage(firstDamage)
                    }

                    for (damageEvent in hits) {
                        if (knockbackComponent != null) {
                            damageEvent.putMetaObject<KnockbackComponent?>(
                                Damage.KNOCKBACK_COMPONENT,
                                knockbackComponent
                            )
                        }

                        var damageValue = damageEvent.getAmount()
                        damageValue += armorDamageModifiers[0]
                        damageEvent.amount = damageValue * max(0.0f, armorDamageModifiers[1])
                        damageEvent.putMetaObject<Vector4d?>(Damage.HIT_LOCATION, hit)

                        commandBuffer.invoke<Damage?>(targetRef, damageEvent)
                    }
                }
            }
        }
    }

    private fun calculateKnockbackAndArmorModifiers(
        @Nonnull damageClass: DamageClass,
        @Nonnull damage: Object2FloatMap<DamageCause?>,
        @Nonnull targetRef: Ref<EntityStore?>,
        @Nonnull attackerRef: Ref<EntityStore?>,
        armorDamageModifiers: FloatArray,
        knockbackMultiplier: DoubleArray,
        @Nonnull componentAccessor: ComponentAccessor<EntityStore?>
    ) {
        val effectControllerComponent = componentAccessor.getComponent<EffectControllerComponent?>(
            targetRef,
            EffectControllerComponent.getComponentType()
        )
        if (effectControllerComponent != null) {
            knockbackMultiplier[0] =
                IntStream.of(*effectControllerComponent.getActiveEffectIndexes()).mapToObj<EntityEffect?>(
                    IntFunction { ix: Int ->
                        (EntityEffect.getAssetStore()
                            .getAssetMap() as IndexedLookupTableAssetMap<*, *>).getAsset(ix) as EntityEffect?
                    }).filter { effect: EntityEffect? -> effect != null && effect.getApplicationEffects() != null }
                    .mapToDouble { effect: EntityEffect? ->
                        effect!!.getApplicationEffects()!!.getKnockbackMultiplier().toDouble()
                    }.reduce(1.0, DoubleBinaryOperator { a: Double, b: Double -> a * b })
        }

        val armorComponent = componentAccessor.getComponent<Armor?>(attackerRef, Armor.getComponentType())
        if (armorComponent != null) {
            val armorContainer = armorComponent.getInventory()
            var knockbackEnhancementModifier = 1.0f

            for (i in 0..<armorContainer.capacity) {
                val itemStack = armorContainer.getItemStack(i.toShort())
                if (itemStack != null && !itemStack.isEmpty) {
                    val item = itemStack.item
                    if (item.getArmor() != null) {
                        val armorDamageEnhancementMap = item.getArmor().getDamageEnhancementValues()
                        val var15: ObjectIterator<*> = damage.keys.iterator()

                        while (var15.hasNext()) {
                            val damageCause = var15.next() as DamageCause?
                            if (armorDamageEnhancementMap != null) {
                                val armorDamageEnhancementValue =
                                    armorDamageEnhancementMap.get(damageCause) as Array<StaticModifier>?
                                if (armorDamageEnhancementValue != null) {
                                    for (staticModifier in armorDamageEnhancementValue) {
                                        if (staticModifier.getCalculationType() === StaticModifier.CalculationType.ADDITIVE) {
                                            armorDamageModifiers[0] += staticModifier.getAmount()
                                        } else {
                                            armorDamageModifiers[1] += staticModifier.getAmount()
                                        }
                                    }
                                }
                            }

                            val knockbackEnhancements = item.getArmor().getKnockbackEnhancements()
                            if (knockbackEnhancements != null) {
                                knockbackEnhancementModifier += knockbackEnhancements[damageCause]!!
                            }
                        }

                        val damageClassModifier =
                            item.getArmor().getDamageClassEnhancement()[damageClass] as Array<StaticModifier>?
                        if (damageClassModifier != null) {
                            for (modifier in damageClassModifier) {
                                if (modifier.getCalculationType() === StaticModifier.CalculationType.ADDITIVE) {
                                    armorDamageModifiers[0] += modifier.getAmount()
                                } else {
                                    armorDamageModifiers[1] += modifier.getAmount()
                                }
                            }
                        }
                    }
                }
            }

            knockbackMultiplier[0] *= knockbackEnhancementModifier.toDouble()
        }
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(
        DieselProjectileComponent.TYPE,
        TransformComponent.getComponentType(),
        BoundingBox.getComponentType()
    )
}