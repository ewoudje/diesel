package com.nsane.diesel.flying

import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class AirSimulator: Resource<EntityStore?> {
    var flying: Boolean = false
    var worldInShipPosition: Vector3d = Vector3d(0.0, 80.0, 0.0)
    var shipPosition: Vector3d = Vector3d(0.0, 0.0, 0.0)
    var shipRotation: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    var velocityModifier = 1.0
    var distanceTraveled = 0.0

    override fun clone(): Resource<EntityStore?>? = AirSimulator()

    companion object {
        val CODEC = buildCodec(::AirSimulator) {

        }

        val TYPE by lazy { DieselPlugin.getResource(AirSimulator::class.java) }
    }
}
