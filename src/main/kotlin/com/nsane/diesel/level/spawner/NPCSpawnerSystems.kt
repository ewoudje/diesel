package com.nsane.diesel.level.spawner

import com.hypixel.hytale.component.*
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.modules.block.BlockModule
import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.npc.NPCPlugin
import com.nsane.diesel.WorldEventEntitySystem
import com.nsane.diesel.level.ChangeLevelEvent
import com.nsane.diesel.level.LevelManager

object NPCSpawnerRefSystem : RefSystem<ChunkStore?>() {
    override fun onEntityAdded(
        ref: Ref<ChunkStore?>,
        var2: AddReason,
        var3: Store<ChunkStore?>,
        buffer: CommandBuffer<ChunkStore?>
    ) {
        val spawner = buffer.getComponent(ref, NPCSpawner.TYPE)!!
        if (spawner.level == null) {
            val manager = buffer.externalData.world.entityStore.store.getResource(LevelManager.TYPE)
            spawner.level = manager.currentLevel?.name
        }
    }

    override fun onEntityRemove(
        var1: Ref<ChunkStore?>,
        var2: RemoveReason,
        var3: Store<ChunkStore?>,
        var4: CommandBuffer<ChunkStore?>
    ) {

    }

    override fun getQuery(): Query<ChunkStore?>? = NPCSpawner.TYPE

}

object NPCSpawnerSpawnSystem : WorldEventEntitySystem<ChunkStore?, ChangeLevelEvent>(ChangeLevelEvent::class.java) {
    override fun getQuery(): Query<ChunkStore?>? = NPCSpawner.TYPE
    override fun handle(
        buffer: CommandBuffer<ChunkStore?>,
        idx: Int,
        chunk: ArchetypeChunk<ChunkStore?>,
        event: ChangeLevelEvent
    ) {
        val spawner = chunk.getComponent(idx, NPCSpawner.TYPE)!!
        if (spawner.level == event.newLevel.name) {
            val info = chunk.getComponent(idx, BlockModule.BlockStateInfo.getComponentType())!!
            val blockChunk = buffer.getComponent(info.chunkRef, BlockChunk.getComponentType())!!
            val localX = ChunkUtil.xFromBlockInColumn(info.index)
            val localY = ChunkUtil.yFromBlockInColumn(info.index)
            val localZ = ChunkUtil.zFromBlockInColumn(info.index)
            val position = Vector3d(
                localX + (blockChunk.x * 32) + 0.5,
                localY.toDouble(),
                localZ + (blockChunk.z * 32) + 0.5
            )

            val world = buffer.externalData.world
            world.execute {
                NPCPlugin.get().spawnNPC(world.entityStore.store, spawner.npcRole!!, null, position, Vector3f())
            }
        }
    }

}