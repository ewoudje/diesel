package com.nsane.diesel.flying.enviroment


class DeathStarEnvironment: SimpleEnvironment(30) {
    override val weather: String
        get() = "DeathStar"
}