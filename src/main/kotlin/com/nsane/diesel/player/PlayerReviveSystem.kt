package com.nsane.diesel.player

import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefChangeSystem
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entity.component.Interactable
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.interaction.Interactions
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

object PlayerReviveSystem: RefChangeSystem<EntityStore?, DeathComponent>() {
    override fun componentType(): ComponentType<EntityStore?, DeathComponent?> = DeathComponent.getComponentType()

    override fun onComponentAdded(
        ref: Ref<EntityStore?>,
        death: DeathComponent,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val players = buffer.getResource( DieselResource.TYPE)
        val interactions = Interactions()
        interactions.setInteractionId(InteractionType.Use, "Revive")
        buffer.addComponent(ref, Interactable.getComponentType(), Interactable.INSTANCE)
        buffer.addComponent(ref, Interactions.getComponentType(), interactions)
        players.deadPlayers++


        val transform = buffer.getComponent(ref, TransformComponent.getComponentType())!!
        if (transform.position.y <= 1.0) {
            transform.position.assign(0.0, 81.0, 6.0)
        }
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
        val players = buffer.getResource( DieselResource.TYPE)
        buffer.removeComponent(ref, Interactable.getComponentType())
        buffer.removeComponent(ref, Interactions.getComponentType())
        players.deadPlayers--
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(
        DeathComponent.getComponentType(),
        Player.getComponentType()
    )

}