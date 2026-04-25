package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator

class EnvironmentalSpreading(
    val id: String,
    var expectedAmount: Int,
    val spawn: (ComponentAccessor<EntityStore?>) -> Unit,
) {
    private var currentAmount = 0
    private var timeToAdd = 0f

    fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float) {
        val sim = accessor.getResource(AirSimulator.TYPE)

        timeToAdd -= dt
        if (currentAmount < expectedAmount && timeToAdd < 0f) {
            spawn(accessor)
            currentAmount++
            timeToAdd = 400f / sim.shipVelocity.length().toFloat() / expectedAmount
        }
    }

    fun unloaded() {
        currentAmount--
    }
}