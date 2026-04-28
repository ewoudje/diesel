package com.nsane.diesel.logic

import com.nsane.diesel.logic.state_reader.StateReader
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.level.LevelManager
import com.nsane.diesel.logic.bool_computer.BoolComputer
import com.nsane.diesel.logic.pressure_plate.PressurePlate
import kotlin.collections.set

object LogicComponentTracker: RefSystem<ChunkStore?>() {
    private val refs = HashMap<String, Ref<ChunkStore?>>()
    private val custom = HashMap<String, (buffer: ComponentAccessor<ChunkStore?>) -> LogicComponent<*>>()

    override fun onEntityAdded(
        ref: Ref<ChunkStore?>,
        reason: AddReason,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        for (f in find(ref, buffer)) {
            if (f.id.isEmpty()) continue
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

    override fun getQuery(): Query<ChunkStore?> = Query.or(
        StateReader.TYPE,
        BoolComputer.TYPE,
        PressurePlate.TYPE
    )

    private fun find(ref: Ref<ChunkStore?>, buffer: ComponentAccessor<ChunkStore?>): List<LogicComponent<*>> {
        val found = mutableListOf<LogicComponent<*>>()

        buffer.getComponent(ref, StateReader.TYPE)?.let(found::add)
        buffer.getComponent(ref, BoolComputer.TYPE)?.let(found::add)
        buffer.getComponent(ref, PressurePlate.TYPE)?.let(found::add)

        return found
    }

    fun getComponentWithId(buffer: ComponentAccessor<ChunkStore?>, id: String): LogicComponent<*>? {
        if (id.isEmpty()) return null
        synchronized(custom) {
            custom.get(id)?.let { return it(buffer) }
        }

        val ref = getRef(id) ?: return null

        return find(ref, ref.store).find { it.id == id }
    }

    fun getRef(id: String): Ref<ChunkStore?>? = refs[id]

    fun idChanged(id: String, ref: Ref<ChunkStore?>, newId: String) {
        refs.remove(id)
        refs[newId] = ref
    }

    fun clear() {
        refs.clear()
    }

    fun addCustom(key: String, value: String) {
        synchronized(custom) {
            custom[key] = { object : LogicComponent<ChunkStore?> {
                override val id: String = key

                override fun getAsBoolean(): Boolean = true
                override fun getAsString(): String = value
                override fun getAsDouble(): Double = 0.0
                override fun logicUI(
                    playerRef: PlayerRef,
                    selfRef: Ref<ChunkStore?>
                ): CustomUIPage = throw UnsupportedOperationException()

                override fun clone(): Component<ChunkStore?>? = null
            } }
        }
    }

    init {
        custom["level"] = { buffer ->
            object : LogicComponent<ChunkStore?> {
                override val id: String
                    get() = "level"

                private val world = buffer.externalData.world

                override fun getAsBoolean(): Boolean = false
                override fun getAsString(): String =
                    world.entityStore.store.getResource(LevelManager.TYPE).currentLevel?.name ?: "none"

                override fun getAsDouble(): Double = 0.0

                override fun logicUI(
                    playerRef: PlayerRef,
                    selfRef: Ref<ChunkStore?>
                ): CustomUIPage = throw UnsupportedOperationException()

                override fun clone(): Component<ChunkStore?>? = null
            }
        }
    }
}