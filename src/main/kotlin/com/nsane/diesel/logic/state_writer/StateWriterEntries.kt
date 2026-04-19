package com.nsane.diesel.logic.state_writer

import com.hypixel.hytale.codec.Codec
import com.nsane.diesel.logic.bool_computer.BoolComputerEntries
import io.github.hytalekt.kytale.codec.buildCodec

class StateWriterEntries(
    val entryStates: Array<String> = Array(ENTRY_AMOUNT) { "" }
): BoolComputerEntries() {
    companion object {
        const val ENTRY_AMOUNT = 6
        val CODEC = buildCodec(::StateWriterEntries) {
            makeEntries(this, ENTRY_AMOUNT)

            for (i in 1..ENTRY_AMOUNT) {
                addField("@Entry${i}States", Codec.STRING) {
                    setter { entryStates[i - 1] = it }
                    getter { entryStates[i - 1] }
                }
            }
        }
    }
}