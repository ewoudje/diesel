package com.nsane.diesel.logic

import com.nsane.diesel.logic.state_reader.StateReader
import com.nsane.diesel.logic.state_writer.StateWriter
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.logic.bool_computer.BoolComputer
import com.nsane.diesel.logic.pressure_plate.PressurePlate
import io.github.hytalekt.kytale.codec.buildCodec

class OpenLogicUIInteraction : SimpleBlockInteraction() {
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
        val chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z))?.blockComponentChunk ?: return InteractionState.Failed
        val block = chunk.getEntityReference(ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z)) ?: return InteractionState.Failed
        if (!block.isValid) return InteractionState.Failed

        val player = context.commandBuffer?.getComponent(context.entity, Player.getComponentType()) ?: return InteractionState.Failed
        val playerRef = context.commandBuffer?.getComponent(context.entity, PlayerRef.getComponentType()) ?: return InteractionState.Failed
        val store =  context.commandBuffer?.store ?: return InteractionState.Failed

        if (fireEvent) {
            val logicUI = block.store.getComponent(block, StateReader.TYPE)?.logicUI(playerRef, block)
                    ?: block.store.getComponent(block, StateWriter.TYPE)?.logicUI(playerRef, block)
                    ?: block.store.getComponent(block, BoolComputer.TYPE)?.logicUI(playerRef, block)
                    ?: block.store.getComponent(block, PressurePlate.TYPE)?.logicUI(playerRef, block)
                    ?: return InteractionState.Failed
            player.pageManager.openCustomPage(context.entity, store, logicUI)
        }

        return InteractionState.Finished
    }

    companion object {
        val CODEC = buildCodec(::OpenLogicUIInteraction) {

        }
    }
}