package com.nsane.diesel.level.spawner

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.npc.validators.NPCRoleValidator
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.state_writer.StateWriter
import com.nsane.diesel.logic.state_writer.StateWriterEntries
import io.github.hytalekt.kytale.codec.buildCodec

class NPCSpawner: Component<ChunkStore?> {
    var npcRole: String? = null
    var level: String? = null

    override fun clone(): Component<ChunkStore?> = NPCSpawner().also {
        it.npcRole = npcRole
        it.level = level
    }

    companion object {
        val CODEC = buildCodec(::NPCSpawner) {
            documentation = "NPCSpawner"
            addField("NpcRole", Codec.STRING) {
                setter { npcRole = it }
                getter { npcRole }
                addValidator(NPCRoleValidator.INSTANCE)
            }

            addField("Level", Codec.STRING) {
                setter { level = it }
                getter { level }
            }
        }

        val TYPE get() = DieselPlugin.getComponent(NPCSpawner::class.java)
    }
}