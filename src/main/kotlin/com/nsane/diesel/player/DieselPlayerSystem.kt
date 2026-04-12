package com.nsane.diesel.player

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
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
        val playerComponent = store.getComponent(event.playerRef, DieselPlayerComponent.TYPE)
        val hud = event.player.hudManager
        hud.setVisibleHudComponents(ref) // Clears it
        hud.setCustomHud(ref, DieselUIHud(ref))
    }

}