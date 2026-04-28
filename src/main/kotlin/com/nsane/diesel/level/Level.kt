package com.nsane.diesel.level

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.logic.LogicComponentTracker

open class Level(
    val name: String,
    open val objective: String,
    val respawnPoint: Vector3d
) {
    open fun tick(store: ComponentAccessor<EntityStore?>, dt: Float) {

    }
}

class AllDeadLevel(
    name: String,
    objective: String,
    respawnPoint: Vector3d,
    val nextLevel: String
): Level(name, objective, respawnPoint) {
    private var initial = 0f
    override fun tick(store: ComponentAccessor<EntityStore?>, dt: Float) {
        if (initial < 1f) {
            initial += dt
            return
        }

        val levelManager = store.getResource(LevelManager.TYPE)
        if (levelManager.amountOfEnemies <= 0)
            levelManager.enter(nextLevel)
    }
}

class LogicBasedLevel(
    name: String,
    objective: String,
    respawnPoint: Vector3d,
    val logicId: String,
    val nextLevel: String
): Level(name, objective, respawnPoint) {
    override fun tick(store: ComponentAccessor<EntityStore?>, dt: Float) {
        store.externalData.world.execute {
            val logic = LogicComponentTracker.getComponentWithId(store.externalData.world.chunkStore.store, logicId)
            if (logic?.getAsBoolean() ?: false) {
                val levelManager = store.getResource(LevelManager.TYPE)
                levelManager.enter(nextLevel)
            }
        }
    }
}