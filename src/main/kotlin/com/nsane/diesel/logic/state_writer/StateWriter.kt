package com.nsane.diesel.logic.state_writer

import com.nsane.diesel.DieselPlugin
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.logic.state_reader.StateReader
import io.github.hytalekt.kytale.codec.buildCodec


class StateWriter : Component<ChunkStore?> {
    var entries: StateWriterEntries = StateWriterEntries()

    fun logicUI(
        playerRef: PlayerRef,
        selfRef: Ref<ChunkStore>
    ): CustomUIPage = StateWriterPage(playerRef, selfRef)

    override fun clone(): Component<ChunkStore?> = StateWriter()

    companion object {
        val CODEC = buildCodec(::StateWriter) {
            documentation = "StateWriter"
            addField("Entries", StateWriterEntries.CODEC) {
                setter { entries = it ?: StateWriterEntries() }
                getter { entries }
            }
        }

        val TYPE get() =  DieselPlugin.getChunkComponent(StateWriter::class.java)
    }
}