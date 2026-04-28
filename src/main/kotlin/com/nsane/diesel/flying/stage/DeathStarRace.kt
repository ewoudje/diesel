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
        val targetX = (lane - 1) * 30.0
        val strafeSpeed = dt * 30
        val rotateSpeed = (PI.toFloat() / 40f) * dt
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

        if (sim.distanceTraveled > 300.0) {
            if (TARGET_Y - sim.shipPosition.y > RISE_SPEED * dt)
                sim.shipPosition.y += RISE_SPEED * dt
            else
                sim.shipPosition.y = TARGET_Y
        }
    }

    private fun occupies(sim: AirSimulator, lane: Int): Boolean {
        val targetX = (lane - 1) * 30.0
        return abs(sim.shipPosition.x - targetX) < 22.0
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
        sim.shipPosition.y = 0.0
    }

    companion object {
        const val MAX_BANK = PI.toFloat() / 20f
        const val TRAVEL_DISTANCE = 3000.0
        const val HELI_RATE = 20.0
        const val RISE_SPEED = 5.0
        const val TARGET_Y = 110.0
    }
}