package com.nsane.diesel.logic.pressure_plate

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
import com.nsane.diesel.logic.LogicResource
import io.github.hytalekt.kytale.codec.buildCodec

class PressurePlatePage(playerRef: PlayerRef, val readerRef: Ref<ChunkStore?>) :
    InteractiveCustomUIPage<PressurePlatePage.PageData>(
        playerRef,
        CustomPageLifetime.CanDismiss,
        buildCodec(::PageData) {
            addField("@Id", Codec.STRING) {
                setter { id = it }
                getter { id }
            }
        }
    ) {
    override fun handleDataEvent(ref: Ref<EntityStore>, store: Store<EntityStore>, data: PageData) {
        val reader = readerRef.store.getComponent(readerRef, PressurePlate.TYPE) ?: return
        val logic = store.getResource(LogicResource.TYPE)
        if (reader.id != data.id) {
            reader.id = data.id
            logic.idChanged(reader.id, data.id)
        }

        close()
    }

    override fun build(
        ref: Ref<EntityStore?>,
        commands: UICommandBuilder,
        events: UIEventBuilder,
        store: Store<EntityStore?>
    ) {
        val reader = readerRef.store.getComponent(readerRef, PressurePlate.TYPE) ?: return

        commands.append("Pages/PressurePlatePage.ui")
        commands.set("#LogicId.Value", reader.id)

        events.addEventBinding(
            CustomUIEventBindingType.Activating,
            "#ApplyButton",
            EventData().append("@Id", "#LogicId.Value")
        )
    }

    data class PageData(var id: String = "")
}