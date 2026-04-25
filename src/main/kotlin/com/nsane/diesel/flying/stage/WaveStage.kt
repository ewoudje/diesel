package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.HelicopterTickSystem
import com.nsane.diesel.flying.PlaneTickSystem
import com.nsane.diesel.flying.enviroment.SimpleEnvironment
import com.nsane.diesel.flying.enviroment.FlyingEnvironment
import com.nsane.diesel.level.LevelManager

open class WaveStage(
    name: String,
    var planes: Int,
    var helicopters: Int,
    var boarders: Int,
    var zoomies: Int,
    delay: Float,
    val nextLevel: String,
): Stage(name) {
    override val env: FlyingEnvironment = SimpleEnvironment(50)
    private var delay = delay

    override fun setup(
        store: ComponentAccessor<EntityStore?>,
        sim: AirSimulator,
        oldStage: Stage?
    ) {
        repeat(planes) {
            store.addEntity(PlaneTickSystem.buildPlane(sim, store), AddReason.SPAWN)
        }

        repeat(helicopters) {
            store.addEntity(HelicopterTickSystem.buildHelicopter(sim, store), AddReason.SPAWN)
        }
    }

    override fun tick(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, dt: Float) {
        super.tick(store, sim, dt)
        val levelManager = store.getResource(LevelManager.TYPE)
        if (isWaveDead()) {
            delay -= dt
            if (delay <= 0)
                levelManager.enter(nextLevel)
        }

    }

    protected fun isWaveDead() =
        planes == 0 && helicopters == 0 && boarders == 0 && zoomies == 0
}