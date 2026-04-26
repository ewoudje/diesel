package com.nsane.diesel.level

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.logic.LogicComponentTracker
import io.github.hytalekt.kytale.ext.plusAssign
import io.github.hytalekt.kytale.ext.times

open class Level(val name: String, open val objective: String) {
    open fun tick(store: ComponentAccessor<EntityStore?>, dt: Float) {

    }
}

class AllDeadLevel(name: String, objective: String, val nextLevel: String): Level(name, objective) {
    //TODO
}

class LogicBasedLevel(name: String, objective: String, val logicId: String, val nextLevel: String): Level(name, objective) {
    override fun tick(store: ComponentAccessor<EntityStore?>, dt: Float) {
        val logic = LogicComponentTracker.getComponentWithId(store.externalData.world.chunkStore.store, logicId)
        if (logic?.getAsBoolean() ?: false) {
            val levelManager = store.getResource(LevelManager.TYPE)
            levelManager.enter(nextLevel)
        }
    }
}