package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.enviroment.FlyingEnvironment
import com.nsane.diesel.level.Level
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plusAssign
import io.github.hytalekt.kytale.ext.times

abstract class Stage(name: String): Level(name) {
    abstract val env: FlyingEnvironment

    open fun tick(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, dt: Float) {
        val traveled = forward(sim, 10.0) * sim.velocityModifier
        sim.shipVelocity.assign(traveled)
        traveled.scale(dt.toDouble())

        sim.distanceTraveled += traveled.length()
        sim.shipPosition += traveled
    }

    protected fun forward(sim: AirSimulator, speed: Double): Vector3d {
        val r = Vector3d(0.0, 0.0, 1.0)
        r.rotateX(sim.shipRotation.x)
        r.rotateY(sim.shipRotation.y)
        r.rotateZ(sim.shipRotation.z)
        return r.scale(speed)
    }

    protected fun rotateTo(sim: AirSimulator, pitch: Double, yaw: Double, roll: Double, speed: Double): Vector3f {
        val targetRotation = Vector3f(Math.toRadians(pitch).toFloat(), Math.toRadians(yaw).toFloat(), Math.toRadians(roll).toFloat())
        val diff = targetRotation - sim.shipRotation
        val l = diff.length()
        if (l > speed) diff.scale(speed.toFloat() / l)
        if (l < 0.001) return Vector3f.ZERO
        return diff
    }

    abstract fun setup(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, oldStage: Stage?)
}