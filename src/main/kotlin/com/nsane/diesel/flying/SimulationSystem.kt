package com.nsane.diesel.flying

import com.hypixel.hytale.builtin.weather.resources.WeatherResource
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.component.system.WorldEventSystem
import com.hypixel.hytale.component.system.tick.TickingSystem
import com.hypixel.hytale.server.core.asset.type.weather.config.Weather
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.enviroment.EnvironmentalComponent
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.level.ChangeLevelEvent
import kotlin.collections.get

object SimulationSystem : TickingSystem<EntityStore?>() {
    override fun tick(
        dt: Float,
        idx: Int,
        store: Store<EntityStore?>
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        val weather = store.getResource(WeatherResource.getResourceType())

        val weatherIdx = sim.stage?.env?.weather?.let { Weather.getAssetMap().getIndex(it) }
        if (weather.forcedWeatherIndex != weatherIdx)
            weather.setForcedWeather(sim.stage?.env?.weather)
    }

    object OnLevelChange: WorldEventSystem<EntityStore?, ChangeLevelEvent>(ChangeLevelEvent::class.java) {
        override fun handle(
            store: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>,
            event: ChangeLevelEvent
        ) {
            if (event.newLevel is Stage) {
                val sim = store.getResource(AirSimulator.TYPE)
                val oldStage = event.oldLevel as? Stage

                event.newLevel.setup(buffer, sim, oldStage)
            }
        }
    }

    object EnvironmentalRefSystem : RefSystem<EntityStore?>() {
        override fun onEntityAdded(
            ref: Ref<EntityStore?>,
            reason: AddReason,
            store: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>
        ) {
            if (buffer.getComponent(ref, NetworkId.getComponentType()) == null) {
                buffer.addComponent(ref, NetworkId.getComponentType(), NetworkId(store.externalData.takeNextNetworkId()))
            }
            val sim = buffer.getResource(AirSimulator.TYPE)
            val component = buffer.getComponent(ref, EnvironmentalComponent.TYPE)!!

            sim.environmentalAmounts[component.id!!] = 1 + (sim.environmentalAmounts[component.id] ?: 0)
        }

        override fun onEntityRemove(
            ref: Ref<EntityStore?>,
            reason: RemoveReason,
            store: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>
        ) {
            val sim = buffer.getResource(AirSimulator.TYPE)
            val component = buffer.getComponent(ref, EnvironmentalComponent.TYPE)!!

            sim.environmentalAmounts[component.id!!] = sim.environmentalAmounts[component.id]!! - 1
            sim.stage?.env?.environmentalUnloaded(buffer, ref, component)
        }

        override fun getQuery(): Query<EntityStore?>? = EnvironmentalComponent.TYPE
    }
}