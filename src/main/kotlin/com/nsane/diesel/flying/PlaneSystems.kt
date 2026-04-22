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
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
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
import kotlin.math.atan
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object PlaneTickSystem : EntityTickingSystem<EntityStore?>() {
    const val MAX_DISTANCE = 150.0
    const val TURN_SPEED = 0.6f
    const val SPEED = 30.0
    const val PULL_UP = 30.0
    const val FIRE_SPEED = 0.2f

    override fun tick(
        dt: Float,
        idx: Int,
        archTypes: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val sim = buffer.getResource(AirSimulator.TYPE)
        val plane = archTypes.getComponent(idx, PlaneComponent.TYPE)!!
        val simulatedPos = archTypes.getComponent(idx, SimulatedTransformComponent.TYPE)!!

        if (plane.health <= 0) {
            simulatedPos.omega.x = -0.4f
            simulatedPos.omega.y = 0f
            crashingDown(buffer, archTypes.getReferenceTo(idx))
            return
        }

        plane.timeSinceLastBullet += dt

        PhysicsMath.vectorFromAngles(simulatedPos.rotation.y, simulatedPos.rotation.x, simulatedPos.velocity)
        simulatedPos.velocity.scale(SPEED)

        val diff = sim.shipPosition - simulatedPos.position + plane.target
        val distance = diff.length()
        val direction = diff.clone().scale(1.0/distance)
        val dot = direction.dot(simulatedPos.velocity.clone().normalize())
        val yaw = PhysicsMath.headingFromDirection(direction.x, direction.z) - simulatedPos.rotation.y
        val pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z) - simulatedPos.rotation.x

        if (dot > 0.7 && plane.timeSinceLastBullet > FIRE_SPEED) {
            plane.timeSinceLastBullet = 0.0f
            val pos = simulatedPos.position.clone().add(0.0, 0.3, 0.0)
            fire(
                buffer,
                SimulatedTransformationSystem.getWorldPosition(sim, pos),
                SimulatedTransformationSystem.getWorldVelocity(sim, simulatedPos).clone().normalize()
            )
        }


        if (distance < PULL_UP) {
            simulatedPos.omega.x = 0.3f
            simulatedPos.omega.y = 0.0f
            plane.flyingAway = -0.1f
        }

        if (plane.flyingAway < 2.0f) {
            if (plane.flyingAway < 0.0 && distance >= PULL_UP) {
                simulatedPos.omega.y = (Random.nextFloat() - 0.5f) * TURN_SPEED
                simulatedPos.omega.x = -0.03f
                plane.flyingAway = 0.0f
            }

            plane.flyingAway += dt
            return
        }

        simulatedPos.omega.x = max(min(PhysicsMath.normalizeTurnAngle(pitch), TURN_SPEED), -TURN_SPEED)
        simulatedPos.omega.y = max(min(PhysicsMath.normalizeTurnAngle(yaw), TURN_SPEED), -TURN_SPEED)

        simulatedPos.rotation.z += (atan(simulatedPos.omega.y * 9) - simulatedPos.rotation.z) * dt
    }

    fun crashingDown(buffer: CommandBuffer<EntityStore?>, ref: Ref<EntityStore?>) {
        val modelAsset = ModelAsset.getAssetMap().getAsset("CrashingPlane") ?: throw NullPointerException("Plane asset not found")
        val model = Model.createScaledModel(modelAsset, 5.0f)
        buffer.replaceComponent(ref, PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        buffer.replaceComponent(ref, ModelComponent.getComponentType(), ModelComponent(model))
    }

    fun fire(commands: CommandBuffer<EntityStore?>, position: Vector3d, direction: Vector3d) {
        val type = DieselProjectileType.ASSET_STORE.assetMap.getAsset("Plane") ?: throw IllegalArgumentException()
        DieselShootInteraction.shootProjectiles(commands, direction.clone().scale(2.0).add(position), direction, Vector3d(), type, null)
    }

    override fun getQuery(): Query<EntityStore?>? = PlaneComponent.TYPE

    fun buildPlane(sim: AirSimulator): Holder<EntityStore?> {
        val direction = Vector3d(
            (Random.nextDouble() * MAX_DISTANCE * 2) - MAX_DISTANCE,
            0.0,
            MAX_DISTANCE - (Random.nextDouble() * 20)
        ).rotateX(sim.shipRotation.x).rotateY(sim.shipRotation.y).rotateZ(sim.shipRotation.z)

        val modelAsset = ModelAsset.getAssetMap().getAsset("Plane") ?: throw NullPointerException("Plane asset not found")
        val model = Model.createScaledModel(modelAsset, 5.0f)
        val holder = EntityStore.REGISTRY.newHolder()
        holder.addComponent(TransformComponent.getComponentType(), TransformComponent().apply { position.assign(direction) })
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            position.assign(sim.shipPosition + direction)
            rotation.assign(sim.shipRotation.clone())
        })
        holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(PlaneComponent.TYPE)
        return holder
    }
}

object PlaneRefSystem : RefSystem<EntityStore?>() {
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
            DieselPlugin.LOGGER.atWarning().log("Despawned a plane???")
        }
    }

    override fun getQuery(): Query<EntityStore?>? = PlaneComponent.TYPE
}