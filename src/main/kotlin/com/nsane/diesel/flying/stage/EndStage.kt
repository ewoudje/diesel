package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.enviroment.FlyingEnvironment
import com.nsane.diesel.flying.enviroment.SimpleEnvironment
import com.nsane.diesel.level.LevelManager
import com.nsane.diesel.player.TurretComponent

class EndStage : Stage("EndStage", "") {
    override val env: FlyingEnvironment = SimpleEnvironment(0)

    override fun setup(store: ComponentAccessor<EntityStore?>, sim: AirSimulator, oldStage: Stage?) {
        val levelManager = store.getResource(LevelManager.TYPE)
        levelManager.enter("Docks")
        store.externalData.world.playerRefs.forEach {
            store.tryRemoveComponent(it.reference!!, TurretComponent.TYPE)
            store.addComponent(
                it.reference!!,
                Teleport.getComponentType(),
                Teleport.createForPlayer(levelManager.currentLevel!!.respawnPoint, Vector3f())
            )
        }
    }
}