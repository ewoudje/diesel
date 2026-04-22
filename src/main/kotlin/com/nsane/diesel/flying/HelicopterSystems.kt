package com.nsane.diesel.flying

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Holder
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.projectiles.DieselProjectileType
import com.nsane.diesel.projectiles.DieselShootInteraction
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plus
import it.unimi.dsi.fastutil.ints.IntArrayList
import kotlin.math.atan
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object HelicopterTickSystem : EntityTickingSystem<EntityStore?>() {
    const val HANGING_DISTANCE = 10.0
    const val HOVER_DISTANCE = 15.0
    const val FIRING_DISTANCE = 40.0
    const val TURN_SPEED = 0.3
    const val HANG_SPEED = 22.0
    const val FOLLOW_SPEED = 30.0
    const val FIRE_SPEED = 0.2f

    override fun tick(
        dt: Float,
        idx: Int,
        archTypes: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val sim = buffer.getResource(AirSimulator.TYPE)
        val heli = archTypes.getComponent(idx, HelicopterComponent.TYPE)!!
        val simulatedPos = archTypes.getComponent(idx, SimulatedTransformComponent.TYPE)!!

        if (heli.health <= 0) {
            if (heli.flyingAway >= 0) {
                crashingDown(buffer, archTypes.getReferenceTo(idx))
                heli.flyingAway = -1.0f
                sim.helisKilled++
            }
            return
        }

        val diff = sim.shipPosition - simulatedPos.position
        diff.y += heli.hoverHeight
        heli.hoverHeight = max(min(heli.hoverHeight + (Random.nextDouble() * 5.0 * dt), 13.0), 8.0)

        if (Random.nextDouble() / dt < 0.05) heli.hoverLeft = !heli.hoverLeft

        val distance = diff.length()
        val direction = diff.clone().scale(1.0/distance)
        val dir2 = (sim.shipPosition - simulatedPos.position).normalize()

        var targetVelocity = simulatedPos.velocity.clone()
        if (distance < HANGING_DISTANCE) {
            targetVelocity
                .scale(0.93)
                .add(direction.clone().cross(Vector3d.POS_Y).scale(if (heli.hoverLeft) TURN_SPEED else -TURN_SPEED))
        } else if ((distance < FIRING_DISTANCE && heli.hovering) || distance < HOVER_DISTANCE) {
            heli.hovering = true
            targetVelocity.scale(1.1)
        } else {
            heli.hovering = false
            targetVelocity = direction.clone().scale(FOLLOW_SPEED)
        }
        targetVelocity.add(direction.clone().scale((distance - HOVER_DISTANCE) * 0.006 * heli.hoverHeight))

        val length = targetVelocity.length()
        val maxSpeed = if (heli.hovering) HANG_SPEED else FOLLOW_SPEED
        if (length > maxSpeed)
            targetVelocity.scale(maxSpeed/length)

        targetVelocity.add(direction.clone().cross(Vector3d.POS_Y).scale(if (heli.hoverLeft) TURN_SPEED else -TURN_SPEED))
        simulatedPos.velocity.assign(targetVelocity)

        val yaw = PhysicsMath.normalizeAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
        targetVelocity.rotateY(-yaw)

        val pitch = if (distance < FIRING_DISTANCE)
            PhysicsMath.pitchFromDirection(dir2.x, dir2.y, dir2.z)
        else
                (targetVelocity.z / maxSpeed) * Math.PI * 0.04

        val roll = (targetVelocity.x  / -maxSpeed)* Math.PI * 0.04
        simulatedPos.rotation.assign(Vector3f.lerp(
            simulatedPos.rotation,
            Vector3f(pitch.toFloat(), yaw, roll.toFloat()),
            dt
        ))

        heli.timeSinceLastBullet += dt
    }

    fun crashingDown(buffer: CommandBuffer<EntityStore?>, ref: Ref<EntityStore?>) {
        val modelAsset = ModelAsset.getAssetMap().getAsset("CrashingHelicopter") ?: throw NullPointerException("CrashingHelicopter asset not found")
        val model = Model.createScaledModel(modelAsset, 1.0f)
        buffer.replaceComponent(ref, PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        buffer.replaceComponent(ref, ModelComponent.getComponentType(), ModelComponent(model))

        //TODO explosion
    }

    fun fire(commands: CommandBuffer<EntityStore?>, position: Vector3d, direction: Vector3d) {
        val type = DieselProjectileType.ASSET_STORE.assetMap.getAsset("Helicopter") ?: throw IllegalArgumentException()
        //DieselShootInteraction.shootProjectiles(commands, direction.clone().scale(2.0).add(position), direction, Vector3d(), type, null)
    }

    fun buildHelicopter(sim: AirSimulator): Holder<EntityStore?> {
        val direction = Vector3d(
            (Random.nextDouble() * 150 * 2) - 150,
            0.0,
            150 - (Random.nextDouble() * 20)
        ).rotateX(sim.shipRotation.x).rotateY(sim.shipRotation.y).rotateZ(sim.shipRotation.z)

        val sounds = IntArrayList()
        sounds.add(SoundEvent.getAssetMap().getIndex("GyroMotor"))
        val modelAsset = ModelAsset.getAssetMap().getAsset("Helicopter") ?: throw NullPointerException("Helicopter asset not found")
        val model = Model.createScaledModel(modelAsset, 4.0f)
        val holder = EntityStore.REGISTRY.newHolder()
        holder.addComponent(AudioComponent.getComponentType(), AudioComponent(sounds))
        holder.addComponent(TransformComponent.getComponentType(), TransformComponent().apply { position.assign(direction) })
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            position.assign(sim.shipPosition + direction)
            rotation.assign(sim.shipRotation.clone())
        })
        holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(HelicopterComponent.TYPE)
        return holder
    }

    override fun getQuery(): Query<EntityStore?>? = HelicopterComponent.TYPE
}

object HelicopterRefSystem : RefSystem<EntityStore?>() {
    override fun onEntityAdded(
        ref: Ref<EntityStore?>,
        reason: AddReason,
        store: Store<EntityStore?>,
        command: CommandBuffer<EntityStore?>
    ) {
        if (command.getComponent(ref, NetworkId.getComponentType()) == null) {
            command.addComponent(ref, NetworkId.getComponentType(), NetworkId(store.externalData.takeNextNetworkId()))
        }
    }

    override fun onEntityRemove(
        ref: Ref<EntityStore?>,
        reason: RemoveReason,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val sim = buffer.getResource(AirSimulator.TYPE)
        if (reason == RemoveReason.UNLOAD && sim.flying) {
            DieselPlugin.LOGGER.atWarning().log("Despawned a helicopter???")
        }
    }

    override fun getQuery(): Query<EntityStore?>? = HelicopterComponent.TYPE
}