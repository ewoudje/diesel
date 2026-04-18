package com.nsane.diesel.projectiles

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.spatial.SpatialSystem
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.entity.Entity
import com.hypixel.hytale.server.core.entity.EntityUtils
import com.hypixel.hytale.server.core.modules.collision.CharacterCollisionData
import com.hypixel.hytale.server.core.modules.collision.CollisionModule
import com.hypixel.hytale.server.core.modules.collision.CollisionResult
import com.hypixel.hytale.server.core.modules.entity.EntityModule
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.Damage
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.SimulatedTransformComponent
import com.nsane.diesel.flying.SimulatedTransformationSystem
import io.github.hytalekt.kytale.ext.plus

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
        val pos = SimulatedTransformationSystem.getWorldPosition(commands, simulated)

        val collisions = CollisionResult(true, true)
        val entities = mutableListOf<Ref<EntityStore?>>()
        spatial.spatialStructure.collect(pos, 3.0, entities)
        collisions.collisionEntities = entities.mapNotNull { EntityUtils.getEntity(it, commands) }

        CollisionModule.findCollisions(
                boundingBox.boundingBox,
                pos,
                SimulatedTransformationSystem.getWorldVelocity(commands, simulated).scale(dt.toDouble()),
                collisions,
                commands)

        val firstChar = collisions.firstCharacterCollision
        val firstBlock = collisions.firstBlockCollision
        if (firstChar != null) {
            damage(commands, firstChar.entityReference, projectile)
        } else if (firstBlock != null) {

        } else return

        commands.removeEntity(arch.getReferenceTo(idx), RemoveReason.REMOVE)
    }

    fun damage(
        commands: CommandBuffer<EntityStore?>,
        entity: Ref<EntityStore?>,
        projectile: DieselProjectileComponent
    ) {
        val damageSource = Damage.EnvironmentSource("Bullet")
        val damage = Damage(damageSource, DamageCause.PHYSICAL!!, projectile.type!!.bulletDamage)
        DamageSystems.executeDamage(entity, commands, damage)
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(
        DieselProjectileComponent.TYPE,
        TransformComponent.getComponentType(),
        BoundingBox.getComponentType()
    )
}