package com.nsane.diesel.flying

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.MovementType
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselActor
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.flying.stage.StartStage
import com.nsane.diesel.flying.stage.WaveStage
import com.nsane.diesel.player.DieselResource
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.random.Random

class AirSimulator: Resource<EntityStore?> {

    val flying get() = stage != null
    val worldInShipPosition: Vector3d = Vector3d(0.0, 80.0, 0.0)
    val shipPosition: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipVelocity: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipRotation: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    var velocityModifier = 1.0
    var distanceTraveled = 0.0
    var stage: Stage? = null

    fun killedPlane(accessor: ComponentAccessor<EntityStore?>) {
        if (Random.nextDouble() < 0.4) {
            val resource = accessor.getResource(DieselResource.TYPE)
            if (Random.nextDouble() < 0.3)
                resource.broadcastMessage(accessor,  "planeDownSod")
            else
                resource.broadcastMessage(accessor,  "planeDownFred")
        }

        if (stage is WaveStage)
            (stage as WaveStage).planes--
    }

    fun killedHeli(accessor: ComponentAccessor<EntityStore?>) {
        if (Random.nextDouble() < 0.3) {
            val resource = accessor.getResource(DieselResource.TYPE)
            if (Random.nextDouble() < 0.3)
                resource.broadcastMessage(accessor,  "heliDownSod")
            else
                resource.broadcastMessage(accessor,  "heliDownFred")
        }

        if (stage is WaveStage)
                (stage as WaveStage).helicopters--
    }

    override fun clone(): Resource<EntityStore?>? = AirSimulator().also {
        it.stage = stage
        it.velocityModifier = velocityModifier
        it.distanceTraveled = distanceTraveled
        it.worldInShipPosition.assign(worldInShipPosition)
        it.shipPosition.assign(shipPosition)
        it.shipVelocity.assign(shipVelocity)
        it.shipRotation.assign(shipRotation)
    }

    companion object {
        val CODEC = buildCodec(::AirSimulator) {

        }

        val TYPE by lazy { DieselPlugin.getResource(AirSimulator::class.java) }
    }
}
