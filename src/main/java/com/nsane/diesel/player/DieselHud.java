package com.nsane.diesel.player;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.InteractionManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.DieselActor;
import com.nsane.diesel.level.LevelManager;
import li.kelp.vuetale.app.PlayerUi;
import li.kelp.vuetale.app.PlayerUiManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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
        ui.setHudData("playSound", (Consumer<String>) (s) -> {
            playSound(s, store, ref, 1, 1);
        });

    }
    private void playSound(String id, Store<EntityStore> store, Ref<EntityStore> ref, float vol, float pitch) {

        store.getExternalData().getWorld().execute(() -> {
            System.out.println("playSound");

            int soundIndex = SoundEvent.getAssetMap().getIndex("Voice_"+id); //ignore id for now
            SoundUtil.playSoundEvent2dToPlayer(store.getComponent(ref, PlayerRef.getComponentType()),soundIndex,SoundCategory.SFX,1,1);
            /*SoundUtil.playLocalPlayerSoundEvent(
                    store.getComponent(ref, PlayerRef.getComponentType()),
                    soundIndex,0,
                    SoundCategory.SFX, vol, pitch
            );*/
        });
    }
    @FunctionalInterface
    interface SoundConsumer {
        void accept(String s, int[] arr, int a, int b);
    }

private int counter = 0;
    public void onTick(@NotNull CommandBuffer<EntityStore> commands, Ref<EntityStore> ref) {
        LevelManager levelManager = commands.getResource(LevelManager.Companion.getTYPE());
        Player player = commands.getComponent(ref, Player.getComponentType());
        DieselPlayerComponent dieselPlayer = commands.getComponent(ref, DieselPlayerComponent.Companion.getTYPE());
        EntityStatMap entityStatMapComponent = commands.getComponent(ref, EntityStatMap.getComponentType());
        EntityStatValue healthValue = entityStatMapComponent.get(DefaultEntityStatTypes.getHealth());
        EntityStatValue ammo = entityStatMapComponent.get("Shotgun_Scout_Ammo");
        InteractionManager interactionManager = commands.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
        int dashCharges;


        try {
            CooldownHandler cooldownHandler = (CooldownHandler) this.cooldownHandler.get(interactionManager);
            CooldownHandler.Cooldown cooldown = cooldownHandler.getCooldown("Root_Diesel_Dodge_Scout");
            dashCharges = cooldown != null ? chargeCount.getInt(cooldown) : 4;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        ui.setHudData("current_overlay", "WOW");
        if (levelManager.getCurrentLevel() != null) {
            ui.setHudData("objective", levelManager.getCurrentLevel().getObjective());
        } else ui.setHudData("objective", "Loading...");
        int slot = player.getInventory().getActiveHotbarSlot();

        ui.setHudData("dashes", dashCharges);
        ui.setHudData("class", dieselPlayer.getPlayerClass().toString());
        ui.setHudData("hotbarIdx", slot > 1 ? slot > 6 ? 0 : 1 : slot);
        ui.setHudData("ammo", ammo.get());
        ui.setHudData("health", healthValue.asPercentage());
        //ui.setHudData("myFn", (customParam: 'pepes') -> player.kill(customParam)
        //ui.setHudData("fn",showMessage(););

    }

    public void showMessage(@NotNull String chain) {
        ui.setHudData("chain", chain);

    }


    public void die() {
        ui.closeHud();
    }
}
