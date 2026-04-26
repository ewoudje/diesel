package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator

class EnvironmentalSpreading(
    val id: String,
    var expectedAmount: Int,
    val spawn: (ComponentAccessor<EntityStore?>) -> Unit,
) {
    private var timeToAdd = 0f

    fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float) {
        val sim = accessor.getResource(AirSimulator.TYPE)
        val currentAmount = sim.environmentalAmounts[id] ?: 0

        timeToAdd -= dt
        if (currentAmount < expectedAmount && timeToAdd < 0f) {
            spawn(accessor)
            timeToAdd = 400f / sim.shipVelocity.length().toFloat() / expectedAmount
        }
    }
}