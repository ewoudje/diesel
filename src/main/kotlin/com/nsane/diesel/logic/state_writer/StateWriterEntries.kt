package com.nsane.diesel.logic.state_writer

import com.hypixel.hytale.codec.Codec
import com.nsane.diesel.logic.LogicComparison
import com.nsane.diesel.logic.bool_computer.BoolComputerEntries
import io.github.hytalekt.kytale.codec.buildCodec

class StateWriterEntries(
    val entryStates: Array<String> = Array(ENTRY_AMOUNT) { "" },
    entryIds: Array<String> = Array(BoolComputerEntries.Companion.ENTRY_AMOUNT) { "" },
    entryComparisons: Array<LogicComparison> = Array(BoolComputerEntries.Companion.ENTRY_AMOUNT) { LogicComparison.EQUAL },
    entryValues: Array<String> = Array(BoolComputerEntries.Companion.ENTRY_AMOUNT) { "" },
) : BoolComputerEntries(entryIds, entryComparisons, entryValues) {

    override fun clone() = StateWriterEntries(
        this.entryStates.clone(),
        this.entryIds.clone(),
        this.entryComparisons.clone(),
        this.entryValues.clone()
    )

    companion object {
        const val ENTRY_AMOUNT = 6
        val HUD_CODEC = codec("@")
        val CODEC = codec("")

        private fun codec(a: String) =
            buildCodec(::StateWriterEntries) {
                makeEntries(this, ENTRY_AMOUNT, a)

                for (i in 1..ENTRY_AMOUNT) {
                    addField("${a}Entry${i}States", Codec.STRING) {
                        setter { entryStates[i - 1] = it }
                        getter { entryStates[i - 1] }
                    }
                }
            }
    }
}