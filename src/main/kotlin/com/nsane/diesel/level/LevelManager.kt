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

    fun enter(key: String) {
        if (key == currentLevel?.name) return

        currentLevel = when (key) {
            "BossStage" -> BossStage()
            "StartStage" -> StartStage()
            "Stage1" -> WaveStage(
                "Stage1",
                0,
                2,
                0,
                0,
                10f,
                "Stage2"
            )

            "Stage2" -> WaveStage(
                "Stage2",
                1,
                2,
                0,
                0,
                10f,
                "StartStage"
            )

            else -> Level(key)
        }
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