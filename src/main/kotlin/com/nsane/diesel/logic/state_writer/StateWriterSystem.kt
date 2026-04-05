package com.nsane.diesel.logic.state_writer

import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.LogicComparison
import com.nsane.diesel.logic.LogicComponent
import com.nsane.diesel.logic.LogicComponentTracker
import com.nsane.diesel.logic.state_reader.StateReader
import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

object StateWriterSystem: com.hypixel.hytale.component.system.tick.EntityTickingSystem<ChunkStore>() {

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
        val worldChunk = store.getComponent(info.chunkRef, WorldChunk.getComponentType()) ?: return
        val localX = ChunkUtil.xFromBlockInColumn(info.index)
        val localY = ChunkUtil.yFromBlockInColumn(info.index)
        val localZ = ChunkUtil.zFromBlockInColumn(info.index)

        val blockId = blockChunk.getBlock(localX, localY, localZ)
        val type = BlockType.getAssetMap().getAsset(blockId) ?: return


        for (i in 0 until StateWriterEntries.ENTRY_AMOUNT) {
            if (handleEntry(
                    stateWriter.entries.entryIds[i],
                    stateWriter.entries.entryComparisons[i],
                    stateWriter.entries.entryValues[i])) {
                val targetState = stateWriter.entries.entryStates[i]
                if (targetState == (type.getStateForBlock(type) ?: "default")) return

                val newType = type.getBlockKeyForState(targetState) ?:
                    run { DieselPlugin.LOGGER.atInfo().log("State $targetState not found?"); return }

                blockChunk.setBlock(localX, localY, localZ, BlockType.getAssetMap().getIndex(newType), 0, 0)
            }
        }
    }

    private fun handleEntry(id: String, comparison: LogicComparison, value: String): Boolean {
        val value1 = getComponentWithId(id) ?: return false
        val value2 = if (value.startsWith("@"))
            getComponentWithId(value.substring(1))?.getAsString() ?: "Not Found"
        else
            value

        return comparison.between(value1, value2)
    }

    private fun getComponentWithId(id: String): LogicComponent<*>? {
        val ref = LogicComponentTracker.getRef(id) ?: return null
        return ref.store.getComponent(ref, StateReader.TYPE) // TODO
    }

    override fun getQuery(): Query<ChunkStore?> = StateWriter.TYPE
}