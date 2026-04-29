package com.nsane.diesel.logic.state_reader

import com.nsane.diesel.DieselPlugin
import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.logic.LogicResource

object StateReaderSystem: EntityTickingSystem<ChunkStore>() {

    override fun tick(
        dt: Float,
        index: Int,
        chunk: ArchetypeChunk<ChunkStore?>,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        val stateReader = chunk.getComponent(index,StateReader.TYPE) ?: return
        val info = chunk.getComponent(index, BlockModule.BlockStateInfo.getComponentType()) ?: return
        val blockChunk = store.getComponent<BlockChunk?>(info.chunkRef, BlockChunk.getComponentType()) ?: return
        val localX = ChunkUtil.xFromBlockInColumn(info.index)
        val localY = ChunkUtil.yFromBlockInColumn(info.index)
        val localZ = ChunkUtil.zFromBlockInColumn(info.index)

        val blockId = blockChunk.getBlock(localX, localY, localZ)
        val type = BlockType.getAssetMap().getAsset(blockId)

        val logic = store.externalData.world.entityStore.store.getResource(LogicResource.TYPE)
        val before = stateReader.state
        stateReader.state = type?.getStateForBlock(type)
        if (!stateReader.registered) {
            stateReader.registered = true
            logic.initValue(stateReader.id, stateReader.state ?: "default")
        } else if (before != stateReader.state) {
            logic.valueChanged(stateReader.id, stateReader.state ?: "default")
        }
    }

    override fun getQuery(): Query<ChunkStore?> = StateReader.TYPE
}