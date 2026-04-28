package com.nsane.diesel.player

import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefChangeSystem
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entity.component.Interactable
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport
import com.hypixel.hytale.server.core.modules.interaction.Interactions
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.level.LevelManager

object PlayerReviveSystem: RefChangeSystem<EntityStore?, DeathComponent>() {
    override fun componentType(): ComponentType<EntityStore?, DeathComponent?> = DeathComponent.getComponentType()

    override fun onComponentAdded(
        ref: Ref<EntityStore?>,
        death: DeathComponent,
        store: Store<EntityStore?>,
        buffer: CommandBuffer<EntityStore?>
    ) {
        val levelManager = buffer.getResource(LevelManager.TYPE)
        val interactions = Interactions()
        interactions.setInteractionId(InteractionType.Use, "Revive")
        buffer.addComponent(ref, Interactable.getComponentType(), Interactable.INSTANCE)
        buffer.addComponent(ref, Interactions.getComponentType(), interactions)

        val transform = buffer.getComponent(ref, TransformComponent.getComponentType())!!
        if (transform.position.y <= 1.0) {
            val teleport = Teleport.createExact(
                levelManager.currentLevel?.respawnPoint ?: Vector3d(0.0, 200.0, 0.0),
                Vector3f()
            )
            buffer.addComponent(ref, Teleport.getComponentType(), teleport)
            if (levelManager.currentLevel is Stage)
                buffer.removeComponent(ref, DeathComponent.getComponentType())
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
        buffer.removeComponent(ref, Interactable.getComponentType())
        buffer.removeComponent(ref, Interactions.getComponentType())
    }

    override fun getQuery(): Query<EntityStore?>? = Query.and(
        DeathComponent.getComponentType(),
        Player.getComponentType()
    )

}