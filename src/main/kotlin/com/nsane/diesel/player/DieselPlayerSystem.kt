package com.nsane.diesel.player

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.inventory.InventoryComponent
import com.hypixel.hytale.server.core.universe.PlayerRef

object DieselPlayerSystem {

    @JvmStatic
    fun playerReadyEvent(event: PlayerReadyEvent) {
        val world = event.player.world ?: throw IllegalArgumentException("World not set")
        val store = event.playerRef.store
        val ref = store.getComponent(event.playerRef, PlayerRef.getComponentType()) ?: throw IllegalArgumentException()

        if (world.playerCount > 4) {
            ref.packetHandler.disconnect(Message.raw("Too many scrubs on this server!"))
            return
        }

        store.ensureComponent(event.playerRef, DieselPlayerComponent.TYPE)

        val playersResource = store.getResource(DieselPlayersResource.TYPE)
        val playerComponent = store.getComponent(event.playerRef, DieselPlayerComponent.TYPE) ?: throw IllegalArgumentException()
        val hotbar = store.getComponent(event.playerRef, InventoryComponent.HOTBAR_FIRST[0]) ?: throw IllegalArgumentException()
        val hudManager = event.player.hudManager
        val hud = DieselUIHud(ref)

        hudManager.setVisibleHudComponents(ref) // Clears it
        hudManager.setCustomHud(ref, hud)

        hotbar.inventory.registerChangeEvent(hud::onHotbarChange)

        if (playerComponent.playerClass == null) {
            event.player.pageManager.openCustomPage(event.playerRef, store, ClassSelectPage(ref))
        }
    }

}