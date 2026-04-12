package com.nsane.diesel.flying

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.PropComponent
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus


class CloudCommand: AbstractPlayerCommand("cloud", "") {
    override fun execute(
        ctx: CommandContext,
        store: Store<EntityStore?>,
        player: Ref<EntityStore?>,
        playerRef: PlayerRef,
        world: World
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        val modelAsset = ModelAsset.getAssetMap().getAsset("Cloud") ?: throw NullPointerException("Cloud asset not found")
        val model = Model.createScaledModel(modelAsset, 3.0f)
        val holder = EntityStore.REGISTRY.newHolder()
        val transformedPos = playerRef.transform.position - sim.shipPosition

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent())
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(NetworkId.getComponentType(), NetworkId(store.getExternalData().takeNextNetworkId()))
        holder.addComponent(SimulatedPositionComponent.TYPE, SimulatedPositionComponent().apply { position.assign(transformedPos) })
        holder.ensureComponent(UUIDComponent.getComponentType())
        holder.ensureComponent(PropComponent.getComponentType())
        store.addEntity(holder, AddReason.SPAWN)
    }
}