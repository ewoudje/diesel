package com.nsane.diesel.logic

import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

object LogicUtil {
    fun isEntryTrue(buffer: CommandBuffer<ChunkStore?>, id: String, comparison: LogicComparison, value: String): Boolean? {
        if (id.isEmpty()) return null
        val value1 = LogicComponentTracker.getComponentWithId(buffer, id) ?: return false
        val value2 = if (value.startsWith("@"))
            LogicComponentTracker.getComponentWithId(buffer, value.substring(1))?.getAsString() ?: "Not Found"
        else
            value

        return comparison.between(value1, value2)
    }
}