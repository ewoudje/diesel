package com.nsane.diesel.flying

import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.MovementType
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.flying.stage.StartStage
import com.nsane.diesel.flying.stage.WaveStage
import io.github.hytalekt.kytale.codec.buildCodec

class AirSimulator: Resource<EntityStore?> {

    val flying get() = stage != null
    val worldInShipPosition: Vector3d = Vector3d(0.0, 80.0, 0.0)
    val shipPosition: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipVelocity: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipRotation: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    var velocityModifier = 1.0
    var distanceTraveled = 0.0
    var stage: Stage? = null
    var oldStage : Stage? = null

    fun killedPlane() = when(stage) {
        is WaveStage -> (stage as WaveStage).planes--
        else -> {}
    }

    fun killedHeli() = when(stage) {
        is WaveStage -> (stage as WaveStage).helicopters--
        else -> {}
    }

    override fun clone(): Resource<EntityStore?>? = AirSimulator()

    companion object {
        val CODEC = buildCodec(::AirSimulator) {

        }

        val TYPE by lazy { DieselPlugin.getResource(AirSimulator::class.java) }
    }
}
