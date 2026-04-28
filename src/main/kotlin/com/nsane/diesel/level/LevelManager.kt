package com.nsane.diesel.level

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.stage.BossStage
import com.nsane.diesel.flying.stage.DeathStarRace
import com.nsane.diesel.flying.stage.EndStage
import com.nsane.diesel.flying.stage.StartStage
import com.nsane.diesel.flying.stage.WaveStage
import io.github.hytalekt.kytale.codec.buildCodec

class LevelManager : Resource<EntityStore?> {
    private val defaultPoint = Vector3d(200.0, 200.0, 0.0)
    var oldLevel: Level? = null
    var currentLevel: Level? = null

    var amountOfEnemies: Int = 0

    fun enter(key: String) {
        if (key == currentLevel?.name) return

        currentLevel = when (key) {
            "StartOfGame" -> LogicBasedLevel("StartOfGame",
                "Get a job!",
                Vector3d(556.0, 23.0, 94.0),
                "enterOffice",
                "InOffice",
            )
            "InOffice" -> LogicBasedLevel("InOffice",
                "Meet the client",
                Vector3d(557.0, 33.0, 6.0),
                "frontDesk",
                "Briefing"
            )
            "Briefing" -> LogicBasedLevel("Briefing",
                    "Conspire and listen",
                    Vector3d(557.0, 33.0, 6.0),
                    "inOfficeDoor",
                    "Parkour"
            )
            "Parkour" -> LogicBasedLevel("Parkour",
                    "Jump!",
                    Vector3d(550.0, 37.0, -10.0),
                    "parkourDone",
                    "ChaseInStreets"
            )
            "ChaseInStreets" -> LogicBasedLevel(
                "ChaseInStreets",
                "Get to the car!",
                Vector3d(456.0, 13.0, -80.0),
                "atShip",
                "AtShip"
            )
            "Shipyard" -> LogicBasedLevel(
                "Shipyard",
                "Find three levers",
                Vector3d(432.0, 25.0, -13.0),
                "launchAway",
                "StartStage"
            )
            "StartStage" -> StartStage()
            "Stage1" -> WaveStage(
                "Stage1",
                "What are those?",
                0,
                2,
                0,
                0,
                10f,
                "Stage2"
            )
            "Stage2" -> WaveStage(
                "Stage2",
                "What are THOSE?",
                1,
                2,
                0,
                0,
                10f,
                "Stage4"
            )
            "Stage3" -> WaveStage(
                "Stage3",
                "What are THOSE???!!",
                0,
                1,
                3,
                0,
                10f,
                "EndStage"
            )
            "Stage4" -> WaveStage(
                "Stage4",
                "No no no no",
                2,
                3,
                4,
                0,
                10f,
                "EndStage"
            )
            "EndStage" -> EndStage()
            "DeathStarRace" -> DeathStarRace()
            "Docks" -> LogicBasedLevel(
                "Docks",
                "Get into the offices",
                Vector3d(983.0, 57.0, 30.0),
                "openOffice",
                "Offices"
            )
            "Offices" -> LogicBasedLevel(
                "Offices",
                "Reach the chairman",
                Vector3d(1047.0, 63.0, 32.0),
                "todo",
                "TopLevel"
            )
            "TopLevel" -> Level(
                "TopLevel",
                "Show the boss who's boss",
                Vector3d(1063.0, 85.0, 86.0)
            )
            else -> Level(
                key,
                "Let me think...",
                defaultPoint
            )
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