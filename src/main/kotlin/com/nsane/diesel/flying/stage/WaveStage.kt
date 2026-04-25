package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.HelicopterTickSystem
import com.nsane.diesel.flying.PlaneTickSystem
import com.nsane.diesel.flying.enviroment.SimpleEnvironment
import com.nsane.diesel.flying.enviroment.FlyingEnvironment

open class WaveStage(
    name: String,
    var planes: Int,
    var helicopters: Int,
    var boarders: Int,
    var zoomies: Int,
    val nextStage : Stage?
): Stage(name) {
    override val env: FlyingEnvironment = SimpleEnvironment(50)

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
        if (isWaveDead())
            sim.stage = nextStage
    }

    protected fun isWaveDead() =
        planes == 0 && helicopters == 0 && boarders == 0 && zoomies == 0
}