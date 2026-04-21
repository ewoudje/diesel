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
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.CloudTickSystem.buildCloud
import io.github.hytalekt.kytale.ext.plus
import java.rmi.UnexpectedException
import kotlin.math.abs
import kotlin.random.Random

object CloudTickSystem : EntityTickingSystem<EntityStore?>() {
    const val MAX_DISTANCE = 150.0

    override fun tick(
        dt: Float,
        idx: Int,
        archTypes: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val sim = buffer.getResource(AirSimulator.TYPE)
        val cloud = archTypes.getComponent(idx, CloudComponent.TYPE)!!
        val simulatedPos = archTypes.getComponent(idx, SimulatedTransformComponent.TYPE)!!

        cloud.lifetime++
        if (cloud.lifetime > 100000 || simulatedPos.position.distanceSquaredTo(sim.shipPosition) > MAX_DISTANCE * MAX_DISTANCE) {
            buffer.removeEntity(archTypes.getReferenceTo(idx), RemoveReason.REMOVE)
            buffer.addEntity(buildCloud(sim), AddReason.SPAWN)
        }
    }

    fun buildCloud(sim: AirSimulator): Holder<EntityStore?> {
        val direction = Vector3d(
            (Random.nextDouble() * MAX_DISTANCE * 2) - MAX_DISTANCE,
            -(Random.nextDouble() * 20 + 7),
            MAX_DISTANCE - (Random.nextDouble() * 20)
        ).rotateX(sim.shipRotation.x).rotateY(sim.shipRotation.y).rotateZ(sim.shipRotation.z)

        val modelAsset = ModelAsset.getAssetMap().getAsset("Cloud") ?: throw NullPointerException("Cloud asset not found")
        val model = Model.createScaledModel(modelAsset, Random.nextFloat() * 5 + 2)
        val holder = EntityStore.REGISTRY.newHolder()
        holder.addComponent(TransformComponent.getComponentType(), TransformComponent().apply { position.assign(direction) })
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            position.assign(sim.shipPosition + direction)
            velocity.assign(Vector3d(-1.0, 0.0, 0.0))
        })
        holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(CloudComponent.TYPE)
        return holder
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(SimulatedTransformComponent.TYPE, CloudComponent.TYPE)
}

object CloudRefSystem : RefSystem<EntityStore?>() {
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
            buffer.addEntity(buildCloud(sim), AddReason.SPAWN)
        }
    }

    override fun getQuery(): Query<EntityStore?>? = CloudComponent.TYPE
}