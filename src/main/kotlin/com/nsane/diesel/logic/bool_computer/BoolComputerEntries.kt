package com.nsane.diesel.logic.bool_computer

import com.hypixel.hytale.codec.Codec
import com.nsane.diesel.logic.LogicComparison
import io.github.hytalekt.kytale.codec.CodecBuilder
import io.github.hytalekt.kytale.codec.buildCodec

open class BoolComputerEntries(
    val entryIds: Array<String> = Array(ENTRY_AMOUNT) { "" },
    val entryComparisons: Array<LogicComparison> = Array(ENTRY_AMOUNT) { LogicComparison.EQUAL },
    val entryValues: Array<String> = Array(ENTRY_AMOUNT) { "" },
) {
    companion object {
        const val ENTRY_AMOUNT = 6
        val CODEC = buildCodec(::BoolComputerEntries) {
            makeEntries(this, ENTRY_AMOUNT)
        }

        fun <T: BoolComputerEntries> makeEntries(self: CodecBuilder<T>, amount: Int) {
            for (i in 1..amount) {
                self.addField("@Entry${i}Id", Codec.STRING) {
                    setter { entryIds[i - 1] = it }
                    getter { entryIds[i - 1] }
                }

                self.addField("@Entry${i}Comparison", LogicComparison.CODEC) {
                    setter { entryComparisons[i - 1] = it }
                    getter { entryComparisons[i - 1] }
                }

                self.addField("@Entry${i}Value", Codec.STRING) {
                    setter { entryValues[i - 1] = it }
                    getter { entryValues[i - 1] }
                }
            }
        }
    }
}