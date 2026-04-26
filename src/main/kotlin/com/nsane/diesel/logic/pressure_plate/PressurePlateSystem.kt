package com.nsane.diesel.logic.pressure_plate

import com.nsane.diesel.DieselPlugin
import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.shape.Box
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.nsane.diesel.logic.state_reader.StateReader

object PressurePlateSystem: EntityTickingSystem<ChunkStore>() {
    val BOX = Box(
    -1.0, 0.0, -1.0,
        1.0, 4.0, 1.0
    )

    override fun tick(
        dt: Float,
        index: Int,
        chunk: ArchetypeChunk<ChunkStore?>,
        store: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        val pressurePlate = chunk.getComponent(index, PressurePlate.TYPE) ?: return
        val info = chunk.getComponent(index, BlockModule.BlockStateInfo.getComponentType()) ?: return
        val blockChunk = store.getComponent<BlockChunk?>(info.chunkRef, BlockChunk.getComponentType()) ?: return
        val localX = ChunkUtil.xFromBlockInColumn(info.index)
        val y = ChunkUtil.yFromBlockInColumn(info.index)
        val localZ = ChunkUtil.zFromBlockInColumn(info.index)

        val x = blockChunk.x * 32 + localX
        val z = blockChunk.z * 32 + localZ


        val world = store.externalData.world
        pressurePlate.pressedIn = world.playerRefs.all { BOX.containsPosition(
            it.transform.position.x - x,
            it.transform.position.y - y,
            it.transform.position.z - z
        ) }
    }

    override fun getQuery(): Query<ChunkStore?> = PressurePlate.TYPE
}