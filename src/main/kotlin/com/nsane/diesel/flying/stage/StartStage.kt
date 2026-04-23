package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator

class StartStage : Stage() {
    override fun setup(
        store: Store<EntityStore?>,
        sim: AirSimulator,
        oldStage: Stage?
    ) {
        if (oldStage == null) {
            //TODO populate clouds
        }
    }
}