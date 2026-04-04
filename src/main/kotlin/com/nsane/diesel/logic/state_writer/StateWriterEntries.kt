package com.nsane.diesel.logic.state_writer

import com.nsane.diesel.logic.LogicComparison
import com.hypixel.hytale.codec.Codec
import io.github.hytalekt.kytale.codec.buildCodec

class StateWriterEntries(
    val entryIds: Array<String> = Array(ENTRY_AMOUNT) { "" },
    val entryComparisons: Array<LogicComparison> = Array(ENTRY_AMOUNT) { LogicComparison.EQUAL },
    val entryValues: Array<String> = Array(ENTRY_AMOUNT) { "" },
    val entryStates: Array<String> = Array(ENTRY_AMOUNT) { "" }
) {
    companion object {
        const val ENTRY_AMOUNT = 6
        val CODEC = buildCodec(::StateWriterEntries) {
            for (i in 1..ENTRY_AMOUNT) {
                addField("@Entry${i}Id", Codec.STRING) {
                    setter { entryIds[i - 1] = it }
                    getter { entryIds[i - 1] }
                }

                addField("@Entry${i}Comparison", LogicComparison.CODEC) {
                    setter { entryComparisons[i - 1] = it }
                    getter { entryComparisons[i - 1] }
                }

                addField("@Entry${i}Value", Codec.STRING) {
                    setter { entryValues[i - 1] = it }
                    getter { entryValues[i - 1] }
                }

                addField("@Entry${i}States", Codec.STRING) {
                    setter { entryStates[i - 1] = it }
                    getter { entryStates[i - 1] }
                }
            }
        }
    }
}