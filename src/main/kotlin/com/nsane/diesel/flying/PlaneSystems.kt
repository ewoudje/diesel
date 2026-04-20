package com.nsane.diesel.flying

import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil
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
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.DespawnComponent
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.modules.time.TimeResource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.projectiles.DieselProjectileComponent
import com.nsane.diesel.projectiles.DieselProjectileType
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plus
import java.time.Duration
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.min
import kotlin.random.Random

object PlaneTickSystem : EntityTickingSystem<EntityStore?>() {
    const val MAX_DISTANCE = 150.0
    const val TURN_SPEED = 0.06f
    const val SPEED = 25.0
    const val PULL_UP = 40.0
    const val FIRE_SPEED = 0.4f

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
            crashdown()
            return
        }

        PhysicsMath.vectorFromAngles(simulatedPos.rotation.y, simulatedPos.rotation.x, simulatedPos.velocity)
        simulatedPos.velocity.scale(SPEED)

        val diff = sim.shipPosition - simulatedPos.position
        val distance = diff.length()
        val direction = diff.clone().scale(1.0/distance)
        val dot = direction.dot(simulatedPos.velocity.clone().normalize())
        val yaw = PhysicsMath.headingFromDirection(direction.x, direction.z) - simulatedPos.rotation.y
        val pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z) - simulatedPos.rotation.x

        simulatedPos.omega.x = min(PhysicsMath.normalizeTurnAngle(pitch), TURN_SPEED)
        if (distance < PULL_UP) {
            simulatedPos.omega.x = 0.24f
            plane.flyAway = true
        }

        if (plane.flyAway) {
            simulatedPos.omega.y = min(PhysicsMath.normalizeTurnAngle(yaw + Math.PI.toFloat() - 0.5f), TURN_SPEED)
            if (distance > MAX_DISTANCE) {
                plane.flyAway = false
            }

            return
        }


        simulatedPos.omega.y = min(PhysicsMath.normalizeTurnAngle(yaw), TURN_SPEED)
        simulatedPos.rotation.z += (atan(simulatedPos.omega.y * 9) - simulatedPos.rotation.z) * dt

        plane.timeSinceLastBullet += dt
        if (dot > 0.9 && plane.timeSinceLastBullet > FIRE_SPEED) {
            plane.timeSinceLastBullet = 0.0f
            fire(buffer, simulatedPos.position, simulatedPos.velocity.clone().normalize())
        }
    }

    fun crashdown() {

    }

    fun fire(commands: CommandBuffer<EntityStore?>, position: Vector3d, direction: Vector3d) {
        val type = DieselProjectileType.ASSET_STORE.assetMap.getAsset("Plane") ?: throw IllegalArgumentException()
        val yaw = (atan2(-direction.x, -direction.z) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()
        val pitch = (asin(direction.y) + (Random.nextDouble() - 0.5) * Math.toRadians(type.spreadAmount)).toFloat()
        val holder = EntityStore.REGISTRY.newHolder()
        val direction = Vector3d(yaw, pitch)
        val rotation = Vector3f()
        rotation.yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(direction.x, direction.z))
        rotation.pitch = PhysicsMath.pitchFromDirection(direction.x, direction.y, direction.z)

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent(position, rotation))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            this.position.assign(position)
            this.rotation.assign(rotation)
            this.velocity.assign(direction.clone().scale(type.bulletSpeed))
        })
        holder.addComponent(DieselProjectileComponent.TYPE, DieselProjectileComponent().apply { this.type = "Plane" })

        val model = Model.createScaledModel(type.model, 0.3f)
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(BoundingBox.getComponentType(), BoundingBox(model.boundingBox!!))
        holder.addComponent(NetworkId.getComponentType(), NetworkId(commands.getExternalData().takeNextNetworkId()))
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType())

        holder.addComponent(
            DespawnComponent.getComponentType(),
            DespawnComponent(
                commands.getResource<TimeResource?>(TimeResource.getResourceType()).now
                    .plus(Duration.ofMillis(((type.bulletDistance / type.bulletSpeed) * 1000).toLong()))
            )
        )


        commands.addEntity(holder, AddReason.SPAWN)
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