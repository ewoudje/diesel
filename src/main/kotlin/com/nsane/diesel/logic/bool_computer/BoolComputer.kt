package com.nsane.diesel.logic.bool_computer

import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.LogicComponent
import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.logic.BooleanOperator
import io.github.hytalekt.kytale.codec.buildCodec


class BoolComputer(override var id: String = "") : LogicComponent<ChunkStore?> {
    override var registered: Boolean = false
    var entries = BoolComputerEntries()
    var operator = BooleanOperator.OR
    var result: Boolean = false

    override fun getAsString(): String = result.toString()

    override fun logicUI(
        playerRef: PlayerRef,
        selfRef: Ref<ChunkStore?>
    ): CustomUIPage = BoolComputerPage(playerRef, selfRef)

    override fun clone(): Component<ChunkStore?> = BoolComputer(id).also {
        it.entries = entries.clone()
        it.operator = operator
        it.result = result
    }

    companion object {
        val CODEC = buildCodec(::BoolComputer) {
            documentation = "BoolComputer"
            addField("LogicId", Codec.STRING) {
                documentation = "Id of the resulting logic"
                setter { id = it }
                getter { id }
            }

            addField("Entries", BoolComputerEntries.CODEC) {
                setter { entries = it ?: BoolComputerEntries() }
                getter { entries }
            }

            addField("Operator", BooleanOperator.CODEC) {
                setter { operator = it ?: BooleanOperator.OR }
                getter { operator }
            }
        }

        val TYPE get() = DieselPlugin.getComponent(BoolComputer::class.java)
    }
}