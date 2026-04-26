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
            "StartOfGame" -> LogicBasedLevel("StartOfGame", "Get a job!", "enterOffice", "InOffice")
            "InOffice " -> LogicBasedLevel("InOffice ", "Just listen", "talkDone", "ChaseInStreets")
            "ChaseInStreets" -> LogicBasedLevel("ChaseInStreets", "!get OUT!", "endOfStreet", "Shipyard")
            "Shipyard" -> AllDeadLevel("Shipyard", "Kill the guys", "StartStage")
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
            "EnterMech" -> AllDeadLevel("EnterMech", "KILL the security!", "BreakIn")
            "BreakIn" -> LogicBasedLevel("BreakIn", "Go deeper", "readyToBreakIn", "BrokeIn")
            "BrokeIn" -> LogicBasedLevel("BrokeIn", "Find the keycard", "unlockedDoor", "UnlockedDoor")
            "UnlockedDoor" -> LogicBasedLevel("UnlockedDoor", "Get back to the door", "enterKGB", "KGB")
            "KGB" -> AllDeadLevel("KGB", "Kill the feds", "FinalStretch")
            "FinalStretch" -> LogicBasedLevel("FinalStretch", "Go kill Big Boss", "intoBossRoom", "BossFight")
            "BossFight" -> Level(key, "FIGHT TO DEATH")

            else -> Level(key, "No clue.. go kill some feds?")
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