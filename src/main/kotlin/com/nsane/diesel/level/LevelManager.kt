package com.nsane.diesel.level

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.stage.BossStage
import com.nsane.diesel.flying.stage.StartStage
import com.nsane.diesel.flying.stage.WaveStage
import io.github.hytalekt.kytale.codec.buildCodec

class LevelManager : Resource<EntityStore?> {
    var oldLevel: Level? = null
    var currentLevel: Level? = null
        private set

    fun enter(key: String) = when (key) {
        currentLevel?.name -> {}
        "BossStage" -> currentLevel = BossStage()
        "StartStage" -> currentLevel = StartStage()
        "Stage1" -> currentLevel = WaveStage(
            "Stage1",
            0,
            4,
            0,
            0,
            10f,
            "StartStage"
        )
        else -> currentLevel = Level(key)
    }

    override fun clone(): Resource<EntityStore?>? = LevelManager()

    companion object {
        val CODEC = buildCodec(::LevelManager) {
            addField("Level", Codec.STRING) {
                setter { enter(it); oldLevel = currentLevel}
                getter { currentLevel?.name }
            }
        }

        val TYPE by lazy { DieselPlugin.getResource(LevelManager::class.java) }
    }
}