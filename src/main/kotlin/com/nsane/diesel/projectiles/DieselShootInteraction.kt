package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.entity.DespawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig
import com.hypixel.hytale.server.core.modules.projectile.interaction.ProjectileInteraction
import com.hypixel.hytale.server.core.modules.time.TimeResource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.core.util.PositionUtil
import com.hypixel.hytale.server.core.util.TargetUtil
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.SimulatedTransformComponent
import com.nsane.diesel.player.DieselPlayerComponent
import java.time.Duration
import java.util.*
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.random.Random

class DieselShootInteraction: ProjectileInteraction() {
    var projectileType: String? = null
    var offset: Vector3d = Vector3d(0.0, 0.0, 0.0)
    var magazineId: String? = null

    override fun getConfig(): ProjectileConfig? {
        config = DieselProjectileType.ASSET_STORE.assetMap.getAsset(projectileType)!!.configKey
        return super.getConfig()
    }

    override fun firstRun(
        type: InteractionType,
        ctx: InteractionContext,
        cooldown: CooldownHandler
    ) {
        val buffer = ctx.commandBuffer!!
        val clientState = ctx.clientState

        val position: Vector3d
        val direction: Vector3d
        val generatedUUID: UUID?
        if (clientState != null && clientState.attackerPos != null && clientState.attackerRot != null) {
            position = PositionUtil.toVector3d(clientState.attackerPos!!)
            val lookVec = PositionUtil.toRotation(clientState.attackerRot!!)
            direction = Vector3d(lookVec.yaw, lookVec.pitch)
            generatedUUID = clientState.generatedUUID
        } else {
            val lookVec = TargetUtil.getLook(ctx.owningEntity, buffer)
            position = lookVec.position
            direction = lookVec.direction
            generatedUUID = null
        }

        shootProjectiles(
            buffer,
            ctx.owningEntity,
            position,
            direction,
            offset,
            DieselProjectileType.ASSET_STORE.assetMap.getAsset(projectileType)!!,
            generatedUUID
        )
    }

    companion object {
        fun shootProjectiles(
            buffer: CommandBuffer<EntityStore?>,
            owner: Ref<EntityStore?>,
            position: Vector3d,
            direction: Vector3d,
            offset: Vector3d,
            type: DieselProjectileType,
            uuid: UUID? = null,
            onShip: Boolean? = null
        ) {
            val sim = buffer.getResource(AirSimulator.TYPE)
            repeat(type.projectileCount) {
                val yaw = (atan2(-direction.x, -direction.z) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()
                val pitch = (asin(direction.y) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()

                shootProjectile(buffer, type, owner, position, Vector3d(yaw, pitch), offset, if (it == 0) uuid else null, onShip ?: sim.flying)
            }
        }

        private fun shootProjectile(
            commandBuffer: CommandBuffer<EntityStore?>,
            type: DieselProjectileType,
            owner: Ref<EntityStore?>,
            position: Vector3d,
            direction: Vector3d,
            offset: Vector3d,
            uuid: UUID?,
            onShip: Boolean
        ): Ref<EntityStore?> {
            val sim = commandBuffer.getResource(AirSimulator.TYPE)
            val holder = EntityStore.REGISTRY.newHolder()

            val rotation = Vector3f()
            rotation.yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
            rotation.pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z)

            PhysicsMath.vectorFromAngles(rotation.yaw, rotation.pitch, direction)
            val newPosition = position.clone()
                .addScaled(direction, offset.z)

            val xzDir = direction.clone()
            xzDir.y = 0.0
            xzDir.normalize().cross(Vector3d.POS_Y, xzDir)
            newPosition.addScaled(xzDir, offset.x)
            newPosition.addScaled(xzDir.cross(direction, xzDir), offset.y)

            holder.addComponent(TransformComponent.getComponentType(), TransformComponent(newPosition, rotation))
            holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
                setWithWorldPosition(sim, newPosition)
                if (onShip)
                    setWithWorldVelocity(sim, direction.clone().scale(type.bulletSpeed))
                else
                    velocity.assign(direction.clone().scale(type.bulletSpeed))
                this.rotation.assign(rotation)
            })
            holder.addComponent(DieselProjectileComponent.TYPE, DieselProjectileComponent().apply {
                this.type = type.id
                this.owner = owner
            })

            val model = Model.createScaledModel(type.model, 0.5f)
            holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
            holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
            holder.addComponent(BoundingBox.getComponentType(), BoundingBox(model.boundingBox!!))
            holder.addComponent(NetworkId.getComponentType(), NetworkId(commandBuffer.getExternalData().takeNextNetworkId()))

            if (uuid != null) {
                holder.addComponent(UUIDComponent.getComponentType(), UUIDComponent(uuid))
            } else holder.ensureComponent(UUIDComponent.getComponentType())
            holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType())

            holder.addComponent(
                DespawnComponent.getComponentType(),
                DespawnComponent(
                    commandBuffer.getResource<TimeResource?>(TimeResource.getResourceType()).now
                        .plus(Duration.ofMillis(((type.bulletDistance / type.bulletSpeed) * 1000).toLong()))
                )
            )

            //val launchWorldSoundEventIndex = config.getLaunchWorldSoundEventIndex()
            //if (launchWorldSoundEventIndex != 0) {
            //    SoundUtil.playSoundEvent3d(
            //        launchWorldSoundEventIndex,
            //        SoundCategory.SFX,
            //        position.x,
            //        position.y,
            //        position.z,
            //        Predicate { targetRef: Ref<EntityStore?>? -> targetRef != creatorRef },
            //        commandBuffer
            //    )
            //}

            //val projectileSoundEventIndex = config.getProjectileSoundEventIndex()
            //if (projectileSoundEventIndex != 0) {
            //    val audioComponent = AudioComponent()
            //    audioComponent.addSound(projectileSoundEventIndex)
            //    holder.addComponent(AudioComponent.getComponentType(), audioComponent)
            //}

            return commandBuffer.addEntity(holder, AddReason.SPAWN)
        }

        val CODEC = BuilderCodec.builder(DieselShootInteraction::class.java, ::DieselShootInteraction, SimpleInstantInteraction.CODEC)
            .appendInherited(
                KeyedCodec<String>("ProjectileType", Codec.STRING),
                { self: DieselShootInteraction, i: String -> self.projectileType = i },
                { self: DieselShootInteraction -> self.projectileType },
                { o, p -> o.projectileType = p.projectileType }
            )
            .addValidator(DieselProjectileType.VALIDATOR.validator)
            .add()
            .appendInherited(
                KeyedCodec<Vector3d>("Offset", Vector3d.CODEC),
                { self: DieselShootInteraction, i: Vector3d -> self.offset = i },
                { self: DieselShootInteraction -> self.offset },
                { o, p -> o.offset = p.offset }
            )
            .add()
            .appendInherited(
                KeyedCodec<String>("MagazineId", Codec.STRING),
                { self: DieselShootInteraction, i: String -> self.magazineId = i },
                { self: DieselShootInteraction -> self.magazineId },
                { o, p -> o.magazineId = p.magazineId }
            )
            .add()
            .build()
    }
}