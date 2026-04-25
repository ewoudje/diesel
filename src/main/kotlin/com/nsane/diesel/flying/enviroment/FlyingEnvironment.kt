package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator

interface FlyingEnvironment {
    val weather: String

    fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float)
    fun environmentalUnloaded(accessor: ComponentAccessor<EntityStore?>, ref: Ref<EntityStore?>, component: EnvironmentalComponent)
}

object EnvironmentalRefSystem : RefSystem<EntityStore?>() {
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
            val component = buffer.getComponent(ref, EnvironmentalComponent.TYPE)!!
            sim.stage?.env?.environmentalUnloaded(buffer, ref, component)
        }
    }

    override fun getQuery(): Query<EntityStore?>? = EnvironmentalComponent.TYPE
}