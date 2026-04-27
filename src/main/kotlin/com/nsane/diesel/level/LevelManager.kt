package com.nsane.diesel.level

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.stage.BossStage
import com.nsane.diesel.flying.stage.StartStage
import com.nsane.diesel.flying.stage.WaveStage
import io.github.hytalekt.kytale.codec.buildCodec

class LevelManager : Resource<EntityStore?> {
    private val defaultPoint = Vector3d(200.0, 200.0, 0.0)
    var oldLevel: Level? = null
    var currentLevel: Level? = null
        private set

    var amountOfEnemies: Int = 0

    fun enter(key: String) {
        if (key == currentLevel?.name) return

        currentLevel = when (key) {
            "StartOfGame" -> LogicBasedLevel("StartOfGame",
                "Get a job!",
                defaultPoint,
                "enterOffice",
                "InOffice"
            )
            "InOffice" -> LogicBasedLevel("InOffice",
                "Just listen",
                defaultPoint,
                "talkDone",
                "ChaseInStreets"
            )
            "ChaseInStreets" -> LogicBasedLevel(
                "ChaseInStreets",
                "!get OUT!",
                defaultPoint,
                "endOfStreet",
                "Shipyard"
            )
            "Shipyard" -> AllDeadLevel(
                "Shipyard",
                "Kill the guys",
                defaultPoint,
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
                "BossStage"
            )
            "BossStage" -> BossStage()
            "EnterMech" -> AllDeadLevel(
                "EnterMech",
                "KILL the security!",
                defaultPoint,
                "BreakIn"
            )
            "BreakIn" -> LogicBasedLevel(
                "BreakIn",
                "Go deeper",
                defaultPoint,
                "readyToBreakIn",
                "BrokeIn"
            )
            "BrokeIn" -> LogicBasedLevel(
                "BrokeIn",
                "Find the keycard",
                defaultPoint,
                "unlockedDoor",
                "UnlockedDoor"
            )
            "UnlockedDoor" -> LogicBasedLevel(
                "UnlockedDoor",
                "Get back to the door",
                defaultPoint,
                "enterKGB",
                "KGB"
            )
            "KGB" -> AllDeadLevel(
                "KGB",
                "Kill the feds",
                defaultPoint,
                "FinalStretch"
            )
            "FinalStretch" -> LogicBasedLevel(
                "FinalStretch",
                "Go kill Big Boss",
                defaultPoint,
                "intoBossRoom",
                "BossFight"
            )
            "BossFight" -> Level(
                key,
                "FIGHT TO DEATH",
                defaultPoint
            )
            else -> Level(key, "No clue.. go kill some feds?", defaultPoint)
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