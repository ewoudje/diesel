package com.nsane.diesel.player

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
import com.nsane.diesel.logic.state_reader.StateReader
import com.nsane.diesel.player.WelcomePage.PageData
import io.github.hytalekt.kytale.codec.buildCodec

class WelcomePage(playerRef: PlayerRef) : InteractiveCustomUIPage<PageData>(
    playerRef,
    CustomPageLifetime.CanDismissOrCloseThroughInteraction,
    buildCodec(::PageData) {}
) {
    override fun handleDataEvent(ref: Ref<EntityStore>, store: Store<EntityStore>, data: String) {
        close()
    }

    override fun build(
        ref: Ref<EntityStore?>,
        commands: UICommandBuilder,
        events: UIEventBuilder,
        store: Store<EntityStore?>
    ) {
        commands.append("Pages/WelcomePage.ui")

        events.addEventBinding(
            CustomUIEventBindingType.Activating,
            "#Aight",
            EventData()
        )
    }

    class PageData
}