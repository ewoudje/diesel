package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.math.vector.Vector3d
import com.nsane.diesel.flying.AirSimulator
import kotlin.random.Random

open class SimpleEnvironment(expectedClouds: Int): AbstractFlyingEnvironment() {
    override val weather: String
        get() = "Ship"

    private fun randomPosition(sim: AirSimulator): Vector3d {
        val max_distance = 180
        return Vector3d(
            (Random.nextDouble() * max_distance * 2) - max_distance,
            -(Random.nextDouble() * 20 + 10),
            max_distance - (Random.nextDouble() * 20)
        )
            .rotateX(sim.shipRotation.x)
            .rotateY(sim.shipRotation.y)
            .rotateZ(sim.shipRotation.z)
            .add(sim.shipPosition)
    }

    init {
        spreaders.add(EnvironmentalSpreading("Cloud", expectedClouds) {
            spawnCloud(it, randomPosition(it.getResource(AirSimulator.TYPE)))
        })
    }
}