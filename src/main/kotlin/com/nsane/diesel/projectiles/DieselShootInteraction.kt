package com.nsane.diesel.projectiles

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
import com.hypixel.hytale.server.core.modules.entity.DespawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.*
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.physics.component.Velocity
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.modules.time.TimeResource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.core.util.TargetUtil
import com.nsane.diesel.flying.SimulatedTransformComponent
import io.github.hytalekt.kytale.codec.buildCodec
import io.github.hytalekt.kytale.ext.plus
import java.time.Duration
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.random.Random

class DieselShootInteraction: SimpleInstantInteraction() {
    var projectileType: DieselProjectileType? = null

    override fun firstRun(
        type: InteractionType,
        ctx: InteractionContext,
        cooldown: CooldownHandler
    ) {
        val type = projectileType ?: return
        val buffer = ctx.commandBuffer ?: return
        val lookVec = TargetUtil.getLook(ctx.owningEntity, buffer)
        repeat(type.projectileCount) {
            val yaw = (atan2(-lookVec.direction.x, -lookVec.direction.z) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()
            val pitch = (asin(lookVec.direction.y) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()

            spawnProjectile(buffer, type, lookVec.position, Vector3d(yaw, pitch))
        }

        ctx.state.state = InteractionState.Finished
    }


    fun spawnProjectile(
        commandBuffer: CommandBuffer<EntityStore?>,
        type: DieselProjectileType,
        position: Vector3d,
        direction: Vector3d
    ): Ref<EntityStore?> {
        val type = projectileType ?: throw IllegalArgumentException()
        val holder = EntityStore.REGISTRY.newHolder()

        val rotation = Vector3f()
        rotation.yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
        rotation.pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z)

        PhysicsMath.vectorFromAngles(rotation.yaw, rotation.pitch, direction)
        val newPosition = direction.clone()
            .scale(0.8)
            .add(position)

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent(newPosition, rotation))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            this.position.assign(newPosition)
            this.rotation.assign(rotation)
            this.velocity.assign(direction.clone().scale(type.bulletSpeed))
        })
        holder.addComponent(DieselProjectileComponent.TYPE, DieselProjectileComponent().apply { this.type = type })

        val model = Model.createScaledModel(type.model, 0.5f)
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(BoundingBox.getComponentType(), BoundingBox(model.boundingBox!!))
        holder.addComponent(NetworkId.getComponentType(), NetworkId(commandBuffer.getExternalData().takeNextNetworkId()))

        holder.ensureComponent(UUIDComponent.getComponentType())
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


    companion object {
        val CODEC = buildCodec(::DieselShootInteraction) {
            addField("ProjectileType", DieselProjectileType.CODEC) {
                getter { projectileType }
                setter { projectileType = it } }
        }
    }
}