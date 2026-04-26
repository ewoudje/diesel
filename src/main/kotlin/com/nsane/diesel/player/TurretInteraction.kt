package com.nsane.diesel.player

import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.protocol.GameMode
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class TurretInteraction : SimpleBlockInteraction() {
    override fun interactWithBlock(
        p0: World,
        p1: CommandBuffer<EntityStore?>,
        p2: InteractionType,
        p3: InteractionContext,
        p4: ItemStack?,
        p5: Vector3i,
        p6: CooldownHandler
    )  {
        p3.state.state = doInteraction(p2, p3, p0, p5, true)
    }

    override fun simulateInteractWithBlock(
        p0: InteractionType,
        p1: InteractionContext,
        p2: ItemStack?,
        p3: World,
        p4: Vector3i
    ) {
        p1.state.state = doInteraction(p0, p1, p3, p4, false)
    }

    private fun doInteraction(
        type: InteractionType,
        context: InteractionContext,
        world: World,
        targetBlock: Vector3i,
        fireEvent: Boolean
    ): InteractionState {
        val playerRef = context.commandBuffer?.getComponent(context.entity, PlayerRef.getComponentType())  ?: return InteractionState.Failed
        val movementManager = context.commandBuffer?.getComponent(context.entity, MovementManager.getComponentType()) ?: return InteractionState.Failed
        val playerPhysicsValues = context.commandBuffer?.getComponent<PhysicsValues?>(context.entity, PhysicsValues.getComponentType())  ?: return InteractionState.Failed
        val movementConfig = MovementConfig.getAssetMap().getAsset("Turret")!!

        movementManager.setDefaultSettings(
            movementConfig.toPacket(),
            playerPhysicsValues,
            GameMode.Adventure
        )
        movementManager.applyDefaultSettings()
        movementManager.update(playerRef.packetHandler)

        return InteractionState.Finished
    }


    companion object {
        val CODEC = BuilderCodec.builder(TurretInteraction::class.java, ::TurretInteraction, SimpleBlockInteraction.CODEC)
            .build()
    }
}