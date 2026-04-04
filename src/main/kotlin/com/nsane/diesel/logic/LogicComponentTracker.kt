package com.nsane.diesel.logic

import com.nsane.diesel.logic.state_reader.StateReader
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

object LogicComponentTracker: RefSystem<ChunkStore?>() {
    private val refs = HashMap<String, Ref<ChunkStore?>>()

    override fun onEntityAdded(
        ref: Ref<ChunkStore?>,
        reason: AddReason,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        for (f in find(ref, buffer)) {
            refs[f.id] = ref
        }
    }

    override fun onEntityRemove(
        ref: Ref<ChunkStore?>,
        reason: RemoveReason,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        for (f in find(ref, buffer)) {
            refs.remove(f.id)
        }
    }

    override fun getQuery(): Query<ChunkStore?> = Query.or(StateReader.TYPE)

    private fun find(ref: Ref<ChunkStore?>, buffer: CommandBuffer<ChunkStore?>): List<LogicComponent<*>> {
        val found = mutableListOf<LogicComponent<*>>()
        buffer.getComponent(ref, StateReader.TYPE)?.let(found::add)

        return found
    }

    fun getRef(id: String): Ref<ChunkStore?>? = refs[id]

    fun clear() {
        refs.clear()
    }
}