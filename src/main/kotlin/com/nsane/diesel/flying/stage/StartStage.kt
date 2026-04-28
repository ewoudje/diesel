package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.enviroment.SimpleEnvironment
import com.nsane.diesel.flying.enviroment.FlyingEnvironment
import com.nsane.diesel.level.LevelManager
import com.nsane.diesel.logic.LogicComponentTracker

class StartStage : Stage("StartStage", "We on our way") {
    override val env: FlyingEnvironment = SimpleEnvironment(70)

    override fun tickStage(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, dt: Float) {
        super.tickStage(store, sim, dt)

        val logic = LogicComponentTracker.getComponentWithId(store.externalData.world.chunkStore.store, "chain.level.StartStage")
        store.externalData.world.execute {
            if (logic?.getAsBoolean() ?: false) {
                val levelManager = store.getResource(LevelManager.TYPE)
                levelManager.enter("Stage1")
            }
        }
    }

    override fun setup(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, oldStage: Stage?) {
        store.externalData.world.playerRefs.forEach {
            store.addComponent(
                it.reference!!,
                Teleport.getComponentType(),
                Teleport.createForPlayer(respawnPoint, Vector3f())
            )
        }
    }
}