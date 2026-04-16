package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.protocol.SoundCategory
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.InteractionManager
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.DespawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.*
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule
import com.hypixel.hytale.server.core.modules.interaction.Interactions
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.physics.component.Velocity
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.modules.projectile.component.Projectile
import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig
import com.hypixel.hytale.server.core.modules.time.TimeResource
import com.hypixel.hytale.server.core.universe.world.SoundUtil
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.core.util.TargetUtil
import io.github.hytalekt.kytale.codec.buildCodec
import java.time.Duration
import java.util.function.Predicate

class DieselShootInteraction: SimpleInstantInteraction() {
    var config: String? = null

    override fun firstRun(
        type: InteractionType,
        ctx: InteractionContext,
        cooldown: CooldownHandler
    ) {
        val config = ProjectileConfig.getAssetMap().getAsset(this.config) ?: return
        val buffer = ctx.commandBuffer ?: return
        val lookVec = TargetUtil.getLook(ctx.owningEntity, buffer)

        spawnProjectile(ctx.owningEntity, buffer, config, lookVec.position, lookVec.direction)

        ctx.state.state = InteractionState.Finished
    }


    fun spawnProjectile(
        creatorRef: Ref<EntityStore?>?,
        commandBuffer: CommandBuffer<EntityStore?>,
        config: ProjectileConfig,
        position: Vector3d,
        direction: Vector3d
    ): Ref<EntityStore?> {
        val holder = EntityStore.REGISTRY.newHolder()
        val rotation = Vector3f()
        val rotationOffset = config.getSpawnRotationOffset()

        rotation.yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
        rotation.pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z)
        rotation.add(rotationOffset.pitch, rotationOffset.yaw, rotationOffset.roll)

        PhysicsMath.vectorFromAngles(rotation.yaw, rotation.pitch, direction)
        val offset = config.getCalculatedOffset(rotation.pitch, rotation.yaw)

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent(position.add(offset), rotation))
        holder.addComponent(HeadRotation.getComponentType(), HeadRotation(rotation))
        holder.addComponent(Interactions.getComponentType(), Interactions(config.getInteractions()))

        val model = config.getModel()
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(BoundingBox.getComponentType(), BoundingBox(model.boundingBox!!))

        holder.addComponent(NetworkId.getComponentType(), NetworkId(commandBuffer.getExternalData().takeNextNetworkId()))

        holder.ensureComponent(Projectile.getComponentType())
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(Velocity.getComponentType())
        holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType())

        config.getPhysicsConfig().apply(
            holder,
            creatorRef,
            direction.clone().scale(config.getLaunchForce()),
            commandBuffer,
            false
        )

        holder.addComponent(
            DespawnComponent.getComponentType(),
            DespawnComponent(
                commandBuffer.getResource<TimeResource?>(TimeResource.getResourceType()).now
                    .plus(Duration.ofSeconds(300L))
            )
        )

        val launchWorldSoundEventIndex = config.getLaunchWorldSoundEventIndex()
        if (launchWorldSoundEventIndex != 0) {
            SoundUtil.playSoundEvent3d(
                launchWorldSoundEventIndex,
                SoundCategory.SFX,
                position.x,
                position.y,
                position.z,
                Predicate { targetRef: Ref<EntityStore?>? -> targetRef != creatorRef },
                commandBuffer
            )
        }

        val projectileSoundEventIndex = config.getProjectileSoundEventIndex()
        if (projectileSoundEventIndex != 0) {
            val audioComponent = AudioComponent()
            audioComponent.addSound(projectileSoundEventIndex)
            holder.addComponent(AudioComponent.getComponentType(), audioComponent)
        }

        val projectileRef = commandBuffer.addEntity(holder, AddReason.SPAWN)
        if (creatorRef != null) {
            commandBuffer.run { store ->
                onProjectileSpawnInteraction(
                    projectileRef,
                    creatorRef,
                    store
                )
            }
        }

        return projectileRef
    }

    private fun onProjectileSpawnInteraction(
        ref: Ref<EntityStore?>,
        creatorRef: Ref<EntityStore?>,
        store: Store<EntityStore?>
    ) {
        val interactionManagerComponent = store.getComponent<InteractionManager?>(
            creatorRef,
            InteractionModule.get().getInteractionManagerComponent()
        )
        if (interactionManagerComponent != null) {
            val context = InteractionContext.forProxyEntity(interactionManagerComponent, creatorRef, ref, store)
            val rootInteractionId = context.getRootInteractionId(InteractionType.ProjectileSpawn)
            if (rootInteractionId != null) {
                val rootInteraction = RootInteraction.getRootInteractionOrUnknown(rootInteractionId)
                if (rootInteraction != null) {
                    val chain = interactionManagerComponent.initChain(
                        InteractionType.ProjectileSpawn,
                        context,
                        rootInteraction,
                        true
                    )
                    interactionManagerComponent.queueExecuteChain(chain)
                }
            }
        }
    }

    companion object {
        val CODEC = buildCodec(::DieselShootInteraction) {
            addField("Config", Codec.STRING) {
                getter { config }
                setter { config = it }
                addValidator(ProjectileConfig.VALIDATOR_CACHE.getValidator().late())
            }
        }
    }
}