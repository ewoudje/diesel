package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.math.vector.Vector3d
import com.nsane.diesel.flying.AirSimulator
import kotlin.random.Random

class DeathStarEnvironment: SimpleEnvironment(30) {
    override val weather: String
        get() = "DeathStar"
}