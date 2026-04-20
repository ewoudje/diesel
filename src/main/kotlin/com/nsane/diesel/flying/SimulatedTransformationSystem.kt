package com.nsane.diesel.flying

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plus
import io.github.hytalekt.kytale.ext.plusAssign
import java.rmi.UnexpectedException

object SimulatedTransformationSystem : EntityTickingSystem<EntityStore?>() {
    fun getWorldVelocity(store: ComponentAccessor<EntityStore?>, simulated: SimulatedTransformComponent) =
        getWorldVelocity(store.getResource(AirSimulator.TYPE), simulated)

    fun getWorldPosition(store: ComponentAccessor<EntityStore?>, simulated: SimulatedTransformComponent) =
        getWorldPosition(store.getResource(AirSimulator.TYPE), simulated)

    fun getWorldVelocity(sim: AirSimulator, simulated: SimulatedTransformComponent) =
        simulated.velocity.clone()
            .rotateX(-sim.shipRotation.x)
            .rotateY(-sim.shipRotation.y)
            .rotateZ(-sim.shipRotation.z)

    fun getWorldPosition(sim: AirSimulator, simulated: SimulatedTransformComponent): Vector3d =
        (simulated.position - sim.shipPosition)
            .rotateX(-sim.shipRotation.x)
            .rotateY(-sim.shipRotation.y)
            .rotateZ(-sim.shipRotation.z)
            .add(sim.worldInShipPosition)

    override fun tick(
        dt: Float,
        idx: Int,
        archTypes: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val sim = buffer.getResource(AirSimulator.TYPE)
        val transform = archTypes.getComponent(idx, TransformComponent.getComponentType())
            ?: throw UnexpectedException("TransformComponent is null")
        val simulatedPos = archTypes.getComponent(idx, SimulatedTransformComponent.TYPE)
            ?: throw UnexpectedException("SimulatedPositionComponent is null")

        transform.rotation = simulatedPos.rotation - sim.shipRotation
        transform.position = getWorldPosition(sim, simulatedPos)

        simulatedPos.position += simulatedPos.velocity.clone().scale(dt.toDouble())
        simulatedPos.rotation += simulatedPos.omega.clone().scale(dt)
    }
    override fun getQuery(): Query<EntityStore?>? = Query.and(SimulatedTransformComponent.TYPE, TransformComponent.getComponentType())
}