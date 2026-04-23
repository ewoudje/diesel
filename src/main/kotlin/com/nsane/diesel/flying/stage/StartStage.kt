package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeferredCorpseRemoval
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator

class StartStage : Stage() {
    override fun setup(
        store: Store<EntityStore?>,
        sim: AirSimulator,
        oldStage: Stage?
    ) {
        if (oldStage == null) {
            //TODO populate clouds

            cache(store, "CrashingPlane")
            cache(store, "CrashingHelicopter")
        }
    }

    private fun cache(store: Store<EntityStore?>, name: String) {
        val modelAsset = ModelAsset.getAssetMap().getAsset(name) ?: throw NullPointerException("$name asset not found")
        val model = Model.createScaledModel(modelAsset, 0.5f)
        val holder = EntityStore.REGISTRY.newHolder()
        holder.addComponent(TransformComponent.getComponentType(), TransformComponent().apply { position.assign(-155.0,0.0,-155.0) })
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(NetworkId.getComponentType(), NetworkId(store.externalData.takeNextNetworkId()))
        holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
        holder.ensureComponent(UUIDComponent.getComponentType())
        store.addEntity(holder, AddReason.SPAWN)
    }
}