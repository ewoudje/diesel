package com.nsane.diesel.player;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.InteractionManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.HotbarManager;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.DieselActor;
import com.nsane.diesel.DieselPlugin;
import com.nsane.diesel.player.DieselPlayerComponent;
import com.nsane.diesel.player.DieselPlayerSystem;
import li.kelp.vuetale.app.PlayerUi;
import li.kelp.vuetale.app.PlayerUiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.builtins.Commands;

import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.Math.round;

public class DieselHud {
    private static final Field cooldownHandler;
    private static final Field chargeCount;

    static {
        try {
            cooldownHandler = InteractionManager.class.getDeclaredField("cooldownHandler");
            cooldownHandler.setAccessible(true);

            chargeCount = CooldownHandler.Cooldown.class.getDeclaredField("chargeCount");
            chargeCount.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private final PlayerUi ui;

    public DieselHud(Store<EntityStore> store, Ref<EntityStore> ref) {
        ui = PlayerUiManager.INSTANCE.openHud(
                store.getComponent(ref, PlayerRef.getComponentType()),
                ref,
                store,
                "diesel",
                "DieselHud"
        );
    }

private int counter = 0;
    public void onTick(@NotNull CommandBuffer<EntityStore> commands, Ref<EntityStore> ref) {
        Player player = commands.getComponent(ref, Player.getComponentType());
        DieselPlayerComponent dieselPlayer = commands.getComponent(ref, DieselPlayerComponent.Companion.getTYPE());
        EntityStatMap entityStatMapComponent = commands.getComponent(ref, EntityStatMap.getComponentType());
        EntityStatValue healthValue = entityStatMapComponent.get(DefaultEntityStatTypes.getHealth());
        EntityStatValue ammo = entityStatMapComponent.get("Shotgun_Scout_Ammo");
        HotbarManager hotbarManager = player.getHotbarManager();
        InteractionManager interactionManager = commands.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
        int dashCharges;
        counter++;

            try {
                CooldownHandler cooldownHandler = (CooldownHandler) this.cooldownHandler.get(interactionManager);
                CooldownHandler.Cooldown cooldown = cooldownHandler.getCooldown("Root_Diesel_Dodge_Scout");
                dashCharges = cooldown != null ? chargeCount.getInt(cooldown) : 4;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            ui.setHudData("current_overlay", "WOW");
            ui.setHudData("objective", "scout");
            ui.setHudData("dashes", dashCharges);
            ui.setHudData("class", dieselPlayer.getPlayerClass().toString());
            ui.setHudData("hotbarIdx", player.getInventory().getActiveHotbarSlot());
            ui.setHudData("ammo", ammo.get());
            ui.setHudData("health", healthValue.asPercentage());
            //ui.setHudData("myFn", (customParam: 'pepes') -> player.kill(customParam)
            //ui.setHudData("fn",showMessage(););
    }

    public void showMessage(@NotNull DieselActor actor, @NotNull String text, float duration) {
        ui.setHudData("message", text);
        ui.setHudData("messageActor", actor);
        ui.setHudData("messageDuration", duration);
        //ui.setHudData("health", 0.8);
    }


}
