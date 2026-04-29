package com.nsane.diesel.boss

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.codec.buildCodec

class RiseRockInteraction : SimpleBlockInteraction() {

    override fun interactWithBlock(
        p0: World,
        p1: CommandBuffer<EntityStore?>,
        p2: InteractionType,
        p3: InteractionContext,
        p4: ItemStack?,
        p5: Vector3i,
        p6: CooldownHandler
    ) {
        p3.state.state = doInteraction(p2, p3, p0, p5, p1)
    }

    override fun simulateInteractWithBlock(
        p0: InteractionType,
        p1: InteractionContext,
        p2: ItemStack?,
        p3: World,
        p4: Vector3i
    ) {
        p1.state.state = doInteraction(p0, p1, p3, p4, null)
    }

    private fun doInteraction(
        type: InteractionType,
        context: InteractionContext,
        world: World,
        targetBlock: Vector3i,
        buffer: CommandBuffer<EntityStore?>?,
    ): InteractionState {

        val modelAsset = ModelAsset.getAssetMap().getAsset("Rock") ?: throw NullPointerException("Rock asset not found")
        val model = Model.createScaledModel(modelAsset, 3.0f)
        val position =
            com.hypixel.hytale.math.vector.Vector3d(targetBlock.x + 0.5, targetBlock.y - 2.0, targetBlock.z + 0.5)

        val holder = EntityStore.REGISTRY.newHolder()
        holder.addComponent(TransformComponent.getComponentType(), TransformComponent(position, Vector3f()))
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(RisenRockComponent.TYPE, RisenRockComponent())
        holder.ensureComponent(UUIDComponent.getComponentType())

        buffer?.addEntity(holder, AddReason.SPAWN)

        return InteractionState.Finished
    }

    companion object {
        val CODEC = buildCodec(::RiseRockInteraction) {

        }
    }
}