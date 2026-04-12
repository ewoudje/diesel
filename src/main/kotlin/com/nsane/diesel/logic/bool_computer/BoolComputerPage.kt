package com.nsane.diesel.logic.bool_computer

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage
import com.hypixel.hytale.server.core.ui.builder.EventData
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.logic.BooleanOperator
import com.nsane.diesel.logic.LogicComponentTracker
import io.github.hytalekt.kytale.codec.buildCodec

class BoolComputerPage(playerRef: PlayerRef, val readerRef: Ref<ChunkStore?>) : InteractiveCustomUIPage<BoolComputerPage.PageData>(
    playerRef,
    CustomPageLifetime.CanDismiss,
    buildCodec(::PageData) {
        addField("@Id", Codec.STRING) {
            setter { id = it }
            getter { id }
        }

        addField("@Operator", BooleanOperator.CODEC) {
            setter { operator = it }
            getter { operator }
        }

        BoolComputerEntries.makeEntries(this, BoolComputerEntries.ENTRY_AMOUNT)
    }
) {
    override fun handleDataEvent(ref: Ref<EntityStore>, store: Store<EntityStore>, data: PageData) {
        val computer = readerRef.store.getComponent(readerRef, BoolComputer.TYPE) ?: return
        if (computer.id != data.id) {
            computer.id = data.id
            LogicComponentTracker.idChanged(computer.id, readerRef, data.id)
        }

        computer.entries = data
        computer.operator = data.operator
        close()
    }

    override fun build(
        ref: Ref<EntityStore?>,
        commands: UICommandBuilder,
        events: UIEventBuilder,
        store: Store<EntityStore?>
    ) {
        val computer = readerRef.store.getComponent(readerRef, BoolComputer.TYPE) ?: return

        commands.append("Pages/BoolComputerPage.ui")
        commands.set("#LogicId.Value", computer.id)
        commands.set("#LogicOperator.Value", computer.operator.toString())

        val data = EventData()
            .append("@Id", "#LogicId.Value")
            .append("@Operator", "#LogicOperator.Value")
        for (i in 1..BoolComputerEntries.ENTRY_AMOUNT) {
            commands.set("#Entry${i} #LogicId.Value", computer.entries.entryIds[i - 1])
            commands.set("#Entry${i} #LogicComparison.Value", computer.entries.entryComparisons[i - 1].name)
            commands.set("#Entry${i} #LogicValue.Value", computer.entries.entryValues[i - 1])

            data.append("@Entry${i}Id", "#Entry${i} #LogicId.Value")
            data.append("@Entry${i}Comparison", "#Entry${i} #LogicComparison.Value")
            data.append("@Entry${i}Value", "#Entry${i} #LogicValue.Value")
        }

        events.addEventBinding(
            CustomUIEventBindingType.Activating,
            "#ApplyButton",
            data
        )
    }

    data class PageData(var id: String = "", var operator: BooleanOperator = BooleanOperator.OR): BoolComputerEntries()
}