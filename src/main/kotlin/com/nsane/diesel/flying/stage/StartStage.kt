package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.enviroment.SimpleEnvironment
import com.nsane.diesel.flying.enviroment.FlyingEnvironment

class StartStage : Stage("StartStage", "We on our way") {
    override val env: FlyingEnvironment = SimpleEnvironment(70)
}