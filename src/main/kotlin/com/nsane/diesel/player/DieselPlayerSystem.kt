package com.nsane.diesel.player

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.component.system.EventSystem
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.protocol.GameMode
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.EntityUtils
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager
import com.hypixel.hytale.server.core.event.events.ecs.SwitchActiveSlotEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.inventory.InventoryComponent
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.WorldEventEntitySystem
import com.nsane.diesel.level.ChangeLevelEvent

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

        val modelAsset = ModelAsset.getAssetMap().getAsset("Prole")!!
        val model = Model.createScaledModel(modelAsset, 1.0f)
        val playersResource = store.getResource(DieselResource.TYPE)
        val playerComponent = store.getComponent(event.playerRef, DieselPlayerComponent.TYPE) ?: throw IllegalArgumentException()
        val hudManager = event.player.hudManager
        playerComponent.playerClass = PlayerClass.SCOUT
        store.replaceComponent(event.playerRef, ModelComponent.getComponentType(), ModelComponent(model))

        if (playerComponent.disable) return

        val hud = DieselHud(store, ref.reference)
        playerComponent.hud = hud

        hudManager.setVisibleHudComponents(ref) // Clears it

        if (playerComponent.playerClass == null) {
            event.player.pageManager.openCustomPage(event.playerRef, store, ClassSelectPage(ref))
        }
    }

    object UILevelCommunication: WorldEventEntitySystem<EntityStore?, ChangeLevelEvent?>(ChangeLevelEvent::class.java) {
        override fun getQuery(): Query<EntityStore?>? = DieselPlayerComponent.TYPE

        override fun handle(
            buffer: CommandBuffer<EntityStore?>,
            idx: Int,
            chunk: ArchetypeChunk<EntityStore?>,
            event: ChangeLevelEvent
        ) {
            val playerComp = chunk.getComponent(idx, DieselPlayerComponent.TYPE)
            playerComp?.hud?.showMessage("level.${event.newLevel.name}")
        }
    }

    private var allDead: Boolean = true

    override fun tick(
        dt: Float,
        archetypeChunk: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        commandBuffer: CommandBuffer<EntityStore?>
    ) {
        super.tick(dt, archetypeChunk, store, commandBuffer)
        if (!archetypeChunk.archetype.contains(DeathComponent.getComponentType()))
            allDead = false
    }

    override fun tick(dt: Float, systemIndex: Int, store: Store<EntityStore?>) {
        allDead = true
        super.tick(dt, systemIndex, store)
        if (allDead && store.externalData.world.playerCount != 0) {
            DieselPlugin.LOGGER.atInfo().log("Everyone Died of ligma :pensive:")
            allDead = false
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
        val movementManager = chunk.getComponent(idx, MovementManager.getComponentType())!!
        val playerRef = chunk.getComponent(idx, PlayerRef.getComponentType())!!
        val isTurret = chunk.getComponent(idx, TurretComponent.TYPE)

        if (!player.disable) {
            val player = chunk.getComponent(idx, Player.getComponentType()) ?: throw IllegalArgumentException()
            val slot = player.inventory.activeHotbarSlot
            val hotbar = player.inventory.hotbar!!

            val movementConfig: MovementConfig
            if (isTurret != null) {
                if (slot != 0.toByte()) {
                    player.inventory.setActiveHotbarSlot(chunk.getReferenceTo(idx), 0, commands)
                }

                val current = hotbar.getItemStack(0)
                if (current == null || current.item.id != "Turret_AA") {
                    hotbar.setItemStackForSlot(0, ItemStack("Turret_AA"))
                }

                movementConfig = MovementConfig.getAssetMap().getAsset("Turret_Movement")!!
            } else {
                if (slot > 1) {
                    player.inventory.setActiveHotbarSlot(chunk.getReferenceTo(idx), if (slot > 6) 0 else 1, commands)
                }

                val current1 = hotbar.getItemStack(0)
                if (current1 == null || current1.item.id != "Shotgun_Scout") {
                    hotbar.setItemStackForSlot(0, ItemStack("Shotgun_Scout"))
                }

                val current2 = hotbar.getItemStack(1)
                if (current2 == null || current2.item.id != "Dagger_Scout") {
                    hotbar.setItemStackForSlot(1, ItemStack("Dagger_Scout"))
                }

                movementConfig = MovementConfig.getAssetMap().getAsset("Diesel_Movement_Scout")!!
            }

            if (movementManager.settings != movementConfig) {
                movementManager.setDefaultSettings(
                    movementConfig.toPacket(),
                    EntityUtils.getPhysicsValues(chunk.getReferenceTo(idx), store),
                    GameMode.Adventure
                )
                movementManager.applyDefaultSettings()
                movementManager.update(playerRef.packetHandler)
            }
        }

        player.hud?.onTick(commands, chunk.getReferenceTo(idx), dt)
    }

    override fun getQuery(): Query<EntityStore?>? = DieselPlayerComponent.TYPE

}