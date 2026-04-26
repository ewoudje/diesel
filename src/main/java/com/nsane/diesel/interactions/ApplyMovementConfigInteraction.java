package com.nsane.diesel.interactions;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;

public class ApplyMovementConfigInteraction extends SimpleInstantInteraction {
    private String movementConfig = null;
    public static final BuilderCodec<ApplyMovementConfigInteraction> CODEC = BuilderCodec.builder(
            ApplyMovementConfigInteraction.class, ApplyMovementConfigInteraction::new, SimpleInstantInteraction.CODEC
    ).documentation("Apply a movement config to the user.")
            .<String>appendInherited(
            new KeyedCodec<>("MovementConfig", Interaction.CHILD_ASSET_CODEC),
            (interaction, s) -> interaction.movementConfig = s,
            interaction -> interaction.movementConfig,
            (interaction, parent) -> interaction.movementConfig = parent.movementConfig)
            .documentation("Set the player's movement config.")
            .addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late())
            .add()
            .build();

    @Override
    protected void firstRun(@NotNull InteractionType interactionType, @NotNull InteractionContext interactionContext, @NotNull CooldownHandler cooldownHandler) {
        CommandBuffer<EntityStore> store = interactionContext.getCommandBuffer();
        Ref<EntityStore> playerReference = interactionContext.getOwningEntity();
        PlayerRef playerRefComponent = store.getComponent(playerReference, PlayerRef.getComponentType());
        assert playerRefComponent != null;
        Player playerComponent = store.getComponent(playerReference, Player.getComponentType());
        assert playerComponent != null;
        PhysicsValues playerPhysicsValues = store.getComponent(playerReference, PhysicsValues.getComponentType());
        MovementConfig movementConfig = MovementConfig.getAssetMap().getAsset(this.movementConfig);

        if (movementConfig != null) {
            MovementManager movementManagerComponent = store.getComponent(playerReference, MovementManager.getComponentType());
            assert movementManagerComponent != null;

            movementManagerComponent.setDefaultSettings(movementConfig.toPacket(), playerPhysicsValues, playerComponent.getGameMode());
            movementManagerComponent.applyDefaultSettings();
            movementManagerComponent.update(playerRefComponent.getPacketHandler());
        }
    }
}
