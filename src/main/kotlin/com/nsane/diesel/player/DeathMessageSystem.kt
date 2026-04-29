package com.nsane.diesel.player

import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefChangeSystem
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

object DeathMessageSystem : RefChangeSystem<EntityStore?, DeathComponent>() {
    override fun componentType(): ComponentType<EntityStore?, DeathComponent?> = DeathComponent.getComponentType()

    override fun onComponentAdded(
        ref: Ref<EntityStore?>,
        death: DeathComponent,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val model = buffer.getComponent(ref, ModelComponent.getComponentType())!!
        val diesel = buffer.getResource(DieselResource.TYPE)
        diesel.broadcastMessage(buffer, "death.${model.model.modelAssetId}")
    }

    override fun onComponentSet(
        var1: Ref<EntityStore?>,
        var2: DeathComponent?,
        var3: DeathComponent,
        var4: Store<EntityStore?>,
        var5: CommandBuffer<EntityStore?>
    ) {

    }

    override fun onComponentRemoved(
        ref: Ref<EntityStore?>,
        var2: DeathComponent,
        var3: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {

    }

    override fun getQuery(): Query<EntityStore?>? = DeathComponent.getComponentType()
}