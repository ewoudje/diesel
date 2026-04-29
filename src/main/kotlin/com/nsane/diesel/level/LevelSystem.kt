package com.nsane.diesel.level

import com.hypixel.hytale.component.*
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefChangeSystem
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.component.system.tick.TickingSystem
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.hypixel.hytale.server.npc.entities.NPCEntity
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.WorldEventEntitySystem
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.HelicopterComponent
import com.nsane.diesel.flying.PlaneComponent
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.logic.LogicResource
import com.nsane.diesel.player.DieselResource

object LevelSystem : TickingSystem<EntityStore?>() {
    override fun tick(
        dt: Float,
        idx: Int,
        store: Store<EntityStore?>
    ) {
        val levelManager = store.getResource(LevelManager.TYPE)
        val dieselResource = store.getResource(DieselResource.TYPE)
        val sim = store.getResource(AirSimulator.TYPE)
        val logic = store.getResource(LogicResource.TYPE)
        if (levelManager.currentLevel == null) return

        if (levelManager.oldLevel != levelManager.currentLevel) {
            val event = ChangeLevelEvent(levelManager.oldLevel, levelManager.currentLevel!!)
            store.invoke(event)
            if (!event.isCancelled)
                store.externalData.world.chunkStore.store.invoke(event)

            if (!event.isCancelled) {
                levelManager.oldLevel = levelManager.currentLevel
                logic.valueChanged("level", levelManager.currentLevel!!.name)
                if (levelManager.currentLevel is Stage) {
                    sim.stage = levelManager.currentLevel as Stage
                } else {
                    sim.stage = null
                }
            } else {
                levelManager.currentLevel = levelManager.oldLevel
            }
        }

        // should only happen on load
        if (sim.stage == null && levelManager.currentLevel is Stage) {
            DieselPlugin.LOGGER.atInfo().log("Setuping stage from load")
            sim.stage = levelManager.currentLevel as Stage
            //TODO wait till ship loaded? sim.stage!!.setup(store, sim, null)
        }

        levelManager.currentLevel?.tick(store, dt)

        if (dieselResource.globalRespawnTimer != Double.MAX_VALUE)
            dieselResource.globalRespawnTimer -= dt

        if (dieselResource.globalRespawnTimer <= -4.0 && levelManager.currentLevel?.name == "DeadLevel") {
            levelManager.enter(dieselResource.deadLevel!!)
        }

        if (dieselResource.globalRespawnTimer <= -5.0) {
            dieselResource.globalRespawnTimer = Double.MAX_VALUE
        }
    }

    object RemoveLevelEntities : WorldEventEntitySystem<EntityStore?, ChangeLevelEvent>(ChangeLevelEvent::class.java) {
        override fun getQuery(): Query<EntityStore?>? = PartOfLevelComponent.TYPE
        override fun handle(
            buffer: CommandBuffer<EntityStore?>,
            idx: Int,
            chunk: ArchetypeChunk<EntityStore?>,
            event: ChangeLevelEvent
        ) {
            buffer.removeEntity(chunk.getReferenceTo(idx), RemoveReason.REMOVE)
        }
    }

    object TrackLevelEntities : RefSystem<EntityStore?>() {
        override fun getQuery(): Query<EntityStore?>? = Query.and(
            Query.or(
                NPCEntity.getComponentType(),
                PlaneComponent.TYPE,
                HelicopterComponent.TYPE
            ),
            Query.not(PartOfLevelComponent.TYPE)
        )

        override fun onEntityAdded(
            ref: Ref<EntityStore?>,
            reason: AddReason,
            store: Store<EntityStore?>,
            buffer: CommandBuffer<EntityStore?>
        ) {
            val levelManager = buffer.getResource(LevelManager.TYPE)
            if (levelManager.currentLevel != null)
                buffer.addComponent(ref, PartOfLevelComponent.TYPE)
        }

        override fun onEntityRemove(
            var1: Ref<EntityStore?>,
            var2: RemoveReason,
            var3: Store<EntityStore?>,
            var4: CommandBuffer<EntityStore?>
        ) {
        }
    }

    object TrackPartOfLevel : RefChangeSystem<EntityStore?, PartOfLevelComponent>() {
        override fun componentType(): ComponentType<EntityStore?, PartOfLevelComponent?> = PartOfLevelComponent.TYPE

        override fun onComponentAdded(
            var1: Ref<EntityStore?>,
            var2: PartOfLevelComponent,
            var3: Store<EntityStore?>,
            commands: CommandBuffer<EntityStore?>
        ) {
            val levelManager = commands.getResource(LevelManager.TYPE)
            levelManager.amountOfEnemies++
        }

        override fun onComponentSet(
            var1: Ref<EntityStore?>,
            var2: PartOfLevelComponent?,
            var3: PartOfLevelComponent,
            var4: Store<EntityStore?>,
            var5: CommandBuffer<EntityStore?>
        ) {

        }

        override fun onComponentRemoved(
            var1: Ref<EntityStore?>,
            var2: PartOfLevelComponent,
            var3: Store<EntityStore?>,
            commands: CommandBuffer<EntityStore?>
        ) {
            val levelManager = commands.getResource(LevelManager.TYPE)
            levelManager.amountOfEnemies--
        }

        override fun getQuery(): Query<EntityStore?>? = PartOfLevelComponent.TYPE
    }
}