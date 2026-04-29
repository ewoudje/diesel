package com.nsane.diesel.logic.state_writer

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType
import com.hypixel.hytale.server.core.ui.builder.EventData
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class StateWriterPage(playerRef: PlayerRef, val readerRef: Ref<ChunkStore>) :
    com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage<StateWriterEntries>(
        playerRef,
        CustomPageLifetime.CanDismiss,
        StateWriterEntries.HUD_CODEC
    ) {
    override fun handleDataEvent(ref: Ref<EntityStore>, store: Store<EntityStore>, data: StateWriterEntries) {
        val writer = readerRef.store.getComponent(readerRef, StateWriter.TYPE) ?: return
        writer.entries = data
        close()
    }

    override fun build(
        ref: Ref<EntityStore?>,
        commands: UICommandBuilder,
        events: UIEventBuilder,
        store: Store<EntityStore?>
    ) {
        val writer = readerRef.store.getComponent(readerRef, StateWriter.TYPE) ?: return

        commands.append("Pages/StateWriterPage.ui")

        val data = EventData()
        for (i in 1..StateWriterEntries.ENTRY_AMOUNT) {
            commands.set("#Entry${i} #LogicId.Value", writer.entries.entryIds[i - 1])
            commands.set("#Entry${i} #LogicComparison.Value", writer.entries.entryComparisons[i - 1].name)
            commands.set("#Entry${i} #LogicValue.Value", writer.entries.entryValues[i - 1])
            commands.set("#Entry${i} #State.Value", writer.entries.entryStates[i - 1])


            data.append("@Entry${i}Id", "#Entry${i} #LogicId.Value")
            data.append("@Entry${i}Comparison", "#Entry${i} #LogicComparison.Value")
            data.append("@Entry${i}Value", "#Entry${i} #LogicValue.Value")
            data.append("@Entry${i}States", "#Entry${i} #State.Value")
        }

        events.addEventBinding(
            CustomUIEventBindingType.Activating,
            "#ApplyButton",
            data
        )
    }
}