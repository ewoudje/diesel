package com.nsane.diesel.logic.state_reader

import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.LogicComponent
import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import io.github.hytalekt.kytale.codec.buildCodec


class StateReader(override var id: String = "") : LogicComponent<ChunkStore?> {
    var state: String? = null

    override fun getAsBoolean(): Boolean = state != null && state != "default"
    override fun getAsString(): String = state ?: "default"
    override fun getAsDouble(): Double = state?.length?.toDouble() ?: -1.0

    override fun logicUI(
        playerRef: PlayerRef,
        selfRef: Ref<ChunkStore?>
    ): CustomUIPage = StateReaderPage(playerRef, selfRef)

    override fun clone(): Component<ChunkStore?> = StateReader(id).also {
        it.state = state
    }

    companion object {
        val CODEC = buildCodec(::StateReader) {
            documentation = "StateReader"
            addField("LogicId", Codec.STRING) {
                documentation = "Id of the logic"
                setter { id = it }
                getter { id }
            }
        }

        val TYPE get() = DieselPlugin.getComponent(StateReader::class.java)
    }
}