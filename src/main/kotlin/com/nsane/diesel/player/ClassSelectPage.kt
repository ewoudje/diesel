package com.nsane.diesel.player

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.codec.buildCodec

class ClassSelectPage(ref: PlayerRef) : InteractiveCustomUIPage<ClassSelectPage.PageData>(
    ref,
    CustomPageLifetime.CantClose,
    buildCodec(::PageData) {
        addField("@PlayerClass", PlayerClass.CODEC) {
            getter { selected }
            setter { selected = it }
        }
    }
) {

    override fun handleDataEvent(ref: Ref<EntityStore?>, store: Store<EntityStore?>, data: PageData) {
        val dieselComponent = store.getComponent(ref, DieselPlayerComponent.TYPE) ?: throw IllegalArgumentException()
        dieselComponent.playerClass = data.selected
        close()
    }

    override fun build(
        ref: Ref<EntityStore?>,
        ui: UICommandBuilder,
        eventBuilder: UIEventBuilder,
        store: Store<EntityStore?>
    ) {
        ui.append("Pages/ClassSelect.ui")

        eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#ChooseButton")
    }

    data class PageData(var selected: PlayerClass? = null)
}