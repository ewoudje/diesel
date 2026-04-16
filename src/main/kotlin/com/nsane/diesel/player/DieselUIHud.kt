package com.nsane.diesel.player

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud
import com.hypixel.hytale.server.core.inventory.container.ItemContainer
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef

class DieselUIHud(ref: PlayerRef) : CustomUIHud(ref) {
    fun onHotbarChange(event: ItemContainer.ItemContainerChangeEvent) {
        show() // Updates UI
    }

    override fun build(ui: UICommandBuilder) {
        ui.append("HUD/CustomHud.ui")
    }
}