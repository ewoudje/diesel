package com.nsane.diesel.flying

import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.system.tick.TickingSystem
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plusAssign
import io.github.hytalekt.kytale.ext.times

object SimulationSystem : TickingSystem<EntityStore?>() {
    override fun tick(
        dt: Float,
        idx: Int,
        store: Store<EntityStore?>
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        val (velocity, omega) = doPathing(sim)

        val traveled = velocity * dt.toDouble() * sim.velocityModifier
        sim.distanceTraveled += traveled.length()
        sim.shipPosition += traveled
        sim.shipRotation += omega * dt * sim.velocityModifier.toFloat()
    }

    private fun doPathing(sim: AirSimulator): Pair<Vector3d, Vector3f> = when (sim.distanceTraveled) {
        in 0.0..10.0 -> Vector3d(0.0, 0.0, 0.1) to rotateTo(sim, 0.0, 0.0, 0.0, 0.2)
        in 10.0..20.0 -> Vector3d(0.1, 0.0, 0.0) to rotateTo(sim, 0.0, 90.0, 0.0, 0.2)
        in 20.0..30.0 -> Vector3d(0.0, 0.0, -0.1) to rotateTo(sim, 0.0, 180.0, 0.0, 0.2)
        in 30.0..40.0 -> Vector3d(-0.1, 0.0, 0.0) to rotateTo(sim, 0.0, 270.0, 0.0, 0.2)
        else -> {
            sim.distanceTraveled = 0.0
            doPathing(sim)
        }
    }

    private fun rotateTo(sim: AirSimulator, pitch: Double, yaw: Double, roll: Double, speed: Double): Vector3f {
        val targetRotation = Vector3f(Math.toRadians(pitch).toFloat(), Math.toRadians(yaw).toFloat(), Math.toRadians(roll).toFloat())
        val diff = targetRotation - sim.shipRotation
        val l = diff.length()
        if (l > speed) diff.scale(speed.toFloat() / l)
        return diff
    }
}