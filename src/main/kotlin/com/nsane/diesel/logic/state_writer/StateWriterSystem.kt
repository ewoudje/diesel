package com.nsane.diesel.logic.state_writer

import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.LogicUtil

object StateWriterSystem: EntityTickingSystem<ChunkStore>() {

    override fun tick(
        dt: Float,
        index: Int,
        chunk: ArchetypeChunk<ChunkStore?>,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        val stateWriter = chunk.getComponent(index, StateWriter.TYPE) ?: return
        val info = chunk.getComponent(index, BlockModule.BlockStateInfo.getComponentType()) ?: return
        val blockChunk = store.getComponent(info.chunkRef, BlockChunk.getComponentType()) ?: return
        val localX = ChunkUtil.xFromBlockInColumn(info.index)
        val localY = ChunkUtil.yFromBlockInColumn(info.index)
        val localZ = ChunkUtil.zFromBlockInColumn(info.index)

        val blockId = blockChunk.getBlock(localX, localY, localZ)
        val type = BlockType.getAssetMap().getAsset(blockId) ?: return


        for (i in 0 until StateWriterEntries.ENTRY_AMOUNT) {
            if (LogicUtil.isEntryTrue(buffer,
                    stateWriter.entries.entryIds[i],
                    stateWriter.entries.entryComparisons[i],
                    stateWriter.entries.entryValues[i]) ?: false) {
                val targetState = stateWriter.entries.entryStates[i]
                if (targetState == (type.getStateForBlock(type) ?: "default")) return

                val newType = type.getBlockKeyForState(targetState) ?:
                    run { DieselPlugin.LOGGER.atInfo().log("State $targetState not found?"); return }

                val sectionIndex = ChunkUtil.indexSection(localY)
                val section = blockChunk.getSectionAtIndex(sectionIndex)


                blockChunk.setBlock(
                    localX, localY, localZ,
                    BlockType.getAssetMap().getIndex(newType),
                    section.getRotationIndex(info.index),
                    section.getFiller(info.index)
                )
            }
        }
    }

    override fun getQuery(): Query<ChunkStore?> = StateWriter.TYPE
}