package com.nsane.diesel.logic

import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

object LogicUtil {
    fun isEntryTrue(
        buffer: CommandBuffer<ChunkStore?>,
        id: String,
        comparison: LogicComparison,
        value: String
    ): Boolean? {
        if (id.isEmpty()) return null
        val logic = buffer.externalData.world.entityStore.store.getResource(LogicResource.TYPE)

        val value1 = logic.getValue(id) ?: return false
        val value2 = if (value.startsWith("@"))
            logic.getValue(value.substring(1)) ?: "Not Found"
        else
            value

        return comparison.between(value1, value2)
    }
}