package com.nsane.diesel.flying

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import java.rmi.UnexpectedException

object SimulatedTransformationSystem : EntityTickingSystem<EntityStore?>() {
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
        val simulatedPos = archTypes.getComponent(idx, SimulatedPositionComponent.TYPE)
            ?: throw UnexpectedException("SimulatedPositionComponent is null")

        val diffPos = simulatedPos.position - sim.shipPosition
        diffPos.rotateX(-sim.shipRotation.x)
        diffPos.rotateY(-sim.shipRotation.y)
        diffPos.rotateZ(sim.shipRotation.z)
        transform.rotation = simulatedPos.rotation - sim.shipRotation
        transform.position = diffPos
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(SimulatedPositionComponent.TYPE, TransformComponent.getComponentType())
}