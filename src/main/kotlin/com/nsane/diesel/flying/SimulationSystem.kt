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
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.world.ParticleUtil
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.enviroment.EnvironmentalComponent
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.level.ChangeLevelEvent
import kotlin.collections.get
import kotlin.random.Random

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

        if (sim.shipHealth <= 40.0 && sim.shipHealthState == 0) {
            sim.shipHealthState = 1
            repeat(3) {
                val pos = sim.shipPosition.clone().add(
                    Random.nextDouble() * 10 - 5,
                    Random.nextDouble() * 2 - 1,
                    Random.nextDouble() * 10 - 5
                )
                ParticleUtil.spawnParticleEffect("Explosion_Big", pos, store)
            }
        } else if (sim.shipHealth <= 15.0 && sim.shipHealthState == 1) {
            sim.shipHealthState = 2
            repeat(5) {
                val pos = sim.shipPosition.clone().add(
                    Random.nextDouble() * 10 - 5,
                    Random.nextDouble() * 2 - 1,
                    Random.nextDouble() * 10 - 5
                )
                ParticleUtil.spawnParticleEffect("Explosion_Big", pos, store)
            }
        } else if (sim.shipHealth <= 0.0 && sim.shipHealthState >= 2) {
            sim.shipHealthState++
            if (sim.shipHealthState % 10 == 0) {
                val pos = sim.shipPosition.clone().add(
                    Random.nextDouble() * 10 - 5,
                    Random.nextDouble() * 2 - 1,
                    Random.nextDouble() * 10 - 5
                )
                ParticleUtil.spawnParticleEffect("Explosion_Big", pos, store)
            }

            if (sim.shipHealthState >= 100) {
                store.externalData.world.playerRefs.forEach {
                    store.addComponent(it.reference!!, DeathComponent.getComponentType())
                }
                sim.shipHealthState = 0
                sim.shipHealth = 100.0
            }
        }
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

            component.id?.let { id -> sim.environmentalAmounts[id] = (sim.environmentalAmounts[id] ?: 0) + 1}
        }

        override fun onEntityRemove(
            ref: Ref<EntityStore?>,
            reason: RemoveReason,
            store: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>
        ) {
            val sim = buffer.getResource(AirSimulator.TYPE)
            val component = buffer.getComponent(ref, EnvironmentalComponent.TYPE)!!

            component.id?.let { id ->
                val i = sim.environmentalAmounts[id]
                if (i != null)
                    sim.environmentalAmounts[id] = i - 1
            }

            sim.stage?.env?.environmentalUnloaded(buffer, ref, component)
        }

        override fun getQuery(): Query<EntityStore?>? = EnvironmentalComponent.TYPE
    }
}