package com.nsane.diesel.flying

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.system.tick.TickingSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import io.github.hytalekt.kytale.ext.plusAssign
import io.github.hytalekt.kytale.ext.times
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@ExperimentalAtomicApi
object SimulationSystem : TickingSystem<EntityStore?>() {
    const val LOAD_RADI = 64

    private val chunks = mutableListOf<WorldChunk>()
    private val chunksReady = AtomicInt(0)

    override fun tick(
        dt: Float,
        idx: Int,
        store: Store<EntityStore?>
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        val (velocity, omega) = Vector3d(0.0, 0.0, 1.0) to Vector3f()//doPathing(sim)

        val traveled = velocity * dt.toDouble() * sim.velocityModifier
        sim.distanceTraveled += traveled.length()
        sim.shipPosition += traveled
        sim.shipRotation += omega * dt * sim.velocityModifier.toFloat()

        /*
        if (chunksReady.load() == 2) {
            chunks.forEach {
                it.resetActiveTimer()
                it.resetKeepAlive()
                it.setFlag(ChunkFlag.TICKING, true)
            }
        } else if (chunksReady.compareAndExchange(0, 1) == 0) {
            repeat(LOAD_RADI * 2) { x ->
                repeat(LOAD_RADI * 2) { z ->
                    val chunk = store.externalData.world.getChunk(ChunkUtil.indexChunk(x - LOAD_RADI, z - LOAD_RADI))
                    chunk!!.addKeepLoaded()
                    chunks.add(chunk!!)
                }
            }

            chunksReady.store(2)
        }*/
    }

    private fun doPathing(sim: AirSimulator): Pair<Vector3d, Vector3f> = when (sim.distanceTraveled) {
        in 0.0..500.0 -> forward(sim, 0.2) to rotateTo(sim, 0.0, 0.0, 0.0, 0.002)
        in 500.0..1000.0 -> forward(sim, 0.2) to rotateTo(sim, 0.0, 90.0, 0.0, 0.002)
        in 1000.0..1500.0 -> forward(sim, 0.2) to rotateTo(sim, 0.0, 180.0, 0.0, 0.002)
        in 1500.0..2000.0 -> forward(sim, 0.2) to rotateTo(sim, 0.0, 270.0, 0.0, 0.002)
        else -> {
            sim.distanceTraveled = 0.0
            doPathing(sim)
        }
    }

    private fun forward(sim: AirSimulator, speed: Double): Vector3d {
        val r = Vector3d(0.0, 0.0, 1.0)
        r.rotateX(sim.shipRotation.x)
        r.rotateY(sim.shipRotation.y)
        r.rotateZ(sim.shipRotation.z)
        return r.scale(speed)
    }

    private fun rotateTo(sim: AirSimulator, pitch: Double, yaw: Double, roll: Double, speed: Double): Vector3f {
        val targetRotation = Vector3f(Math.toRadians(pitch).toFloat(), Math.toRadians(yaw).toFloat(), Math.toRadians(roll).toFloat())
        val diff = targetRotation - sim.shipRotation
        val l = diff.length()
        if (l > speed) diff.scale(speed.toFloat() / l)
        if (l < 0.001) return Vector3f.ZERO
        return diff
    }
}