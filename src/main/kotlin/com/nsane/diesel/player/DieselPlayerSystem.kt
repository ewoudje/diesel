package com.nsane.diesel.player

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.component.system.EventSystem
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.event.events.ecs.SwitchActiveSlotEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.inventory.InventoryComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

object DieselPlayerSystem: EntityTickingSystem<EntityStore?>() {

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

        val playersResource = store.getResource(DieselResource.TYPE)
        val playerComponent = store.getComponent(event.playerRef, DieselPlayerComponent.TYPE) ?: throw IllegalArgumentException()
        val hudManager = event.player.hudManager
        playerComponent.playerClass = PlayerClass.SCOUT

        if (playerComponent.disable) return

        val hud = DieselHud(store, ref.reference)
        playerComponent.hud = hud

        hudManager.setVisibleHudComponents(ref) // Clears it

        if (playerComponent.playerClass == null) {
            event.player.pageManager.openCustomPage(event.playerRef, store, ClassSelectPage(ref))
        }
    }

    object HotbarLimiter: EntityTickingSystem<EntityStore?>() {
        override fun getQuery(): Query<EntityStore?>? = DieselPlayerComponent.TYPE

        override fun tick(
            dt: Float,
            idx: Int,
            chunk: ArchetypeChunk<EntityStore?>,
            var4: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>
        ) {
            val playerComponent = chunk.getComponent(idx, DieselPlayerComponent.TYPE) ?: throw IllegalArgumentException()
            if (!playerComponent.disable) {
                val player = chunk.getComponent(idx, Player.getComponentType()) ?: throw IllegalArgumentException()
                val slot = player.inventory.activeHotbarSlot
                if (slot > 1) {
                    player.inventory.setActiveHotbarSlot(chunk.getReferenceTo(idx), if (slot > 6) 0 else 1, buffer)
                }
            }
        }
    }

    override fun tick(
        dt: Float,
        idx: Int,
        chunk: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        commands: CommandBuffer<EntityStore?>
    ) {
        val player = chunk.getComponent(idx, DieselPlayerComponent.TYPE)!!
        player.hud?.onTick(commands, chunk.getReferenceTo(idx))
    }

    override fun getQuery(): Query<EntityStore?>? = DieselPlayerComponent.TYPE

}