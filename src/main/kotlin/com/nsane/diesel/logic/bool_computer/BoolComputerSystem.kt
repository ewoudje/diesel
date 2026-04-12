package com.nsane.diesel.logic.bool_computer

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.logic.BooleanOperator
import com.nsane.diesel.logic.LogicComparison
import com.nsane.diesel.logic.LogicComponentTracker
import com.nsane.diesel.logic.LogicUtil

object BoolComputerSystem: EntityTickingSystem<ChunkStore>() {

    override fun tick(
        dt: Float,
        index: Int,
        chunk: ArchetypeChunk<ChunkStore?>,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        val computer = chunk.getComponent(index,BoolComputer.TYPE) ?: return
        computer.result = calculateResult(buffer, computer)
    }

    fun calculateResult(buffer: CommandBuffer<ChunkStore?>, computer: BoolComputer): Boolean {
        val operator = computer.operator

        for (i in 0 until BoolComputerEntries.ENTRY_AMOUNT) {
            val r = LogicUtil.isEntryTrue(buffer, computer.entries.entryIds[i], computer.entries.entryComparisons[i], computer.entries.entryValues[i])
            when (operator to r) {
                (BooleanOperator.AND to false) -> return false
                (BooleanOperator.OR to true) -> return true
            }
        }

        return when (operator) {
            BooleanOperator.AND -> true
            BooleanOperator.OR -> false
        }
    }

    override fun getQuery(): Query<ChunkStore?> = BoolComputer.TYPE
}