package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.enviroment.DeathStarEnvironment
import com.nsane.diesel.flying.enviroment.FlyingEnvironment
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class DeathStarRace : Stage("DeathStarRace", "Get in the MECH") {
    override val env: FlyingEnvironment = DeathStarEnvironment()
    var lane: Int = 1
    var isInLane = false

    override fun tickStage(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, dt: Float) {
        val targetX = (lane - 1) * 20.0
        val strafeSpeed = dt * 30
        val rotateSpeed = (PI.toFloat() / 20f) * dt
        isInLane = abs(sim.shipPosition.x - targetX) < strafeSpeed
        if (isInLane) {
            sim.shipPosition.x = targetX
            if (sim.shipRotation.roll > rotateSpeed)
                sim.shipRotation.roll -= rotateSpeed
            else if (sim.shipRotation.roll < -rotateSpeed)
                sim.shipRotation.roll += rotateSpeed
            else sim.shipRotation.roll = 0f
        } else {
            var newRoll = sim.shipRotation.roll
            if (targetX > sim.shipPosition.x) {
                sim.shipPosition.x += strafeSpeed
                newRoll += rotateSpeed
            } else {
                sim.shipPosition.x -= strafeSpeed
                newRoll -= rotateSpeed
            }

            sim.shipRotation.roll = min(max(newRoll, -MAX_BANK), MAX_BANK)
        }

        sim.shipPosition.z += 45 * dt
    }

    override fun setup(
        store: ComponentAccessor<EntityStore?>,
        sim: AirSimulator,
        oldStage: Stage?
    ) {
        super.setup(store, sim, oldStage)
        sim.shipRotation.roll = 0f
        sim.distanceTraveled = 0.0
        sim.shipPosition.x = 0.0
    }

    companion object {
        const val MAX_BANK = PI.toFloat() / 16f
    }
}