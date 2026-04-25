package com.nsane.diesel.flying

import com.hypixel.hytale.builtin.weather.resources.WeatherResource
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.system.WorldEventSystem
import com.hypixel.hytale.component.system.tick.TickingSystem
import com.hypixel.hytale.server.core.asset.type.weather.config.Weather
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.level.ChangeLevelEvent

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

        sim.stage?.tick(store, sim, dt)
        sim.stage?.env?.tick(store, dt)
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
}