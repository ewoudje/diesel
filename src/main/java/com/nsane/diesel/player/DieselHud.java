package com.nsane.diesel.player;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
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
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.flying.AirSimulator;
import com.nsane.diesel.level.LevelManager;
import com.nsane.diesel.logic.LogicResource;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import li.kelp.vuetale.app.PlayerUi;
import li.kelp.vuetale.app.PlayerUiManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    private final IntList sounds = new IntArrayList();

    public DieselHud(Store<EntityStore> store, Ref<EntityStore> ref) {
        ui = PlayerUiManager.INSTANCE.openHud(
                store.getComponent(ref, PlayerRef.getComponentType()),
                ref,
                store,
                "diesel",
                "DieselHud"
        );
        World world = store.getExternalData().getWorld();
        ui.setHudData("playSound", (Consumer<String>) (s) -> {
            synchronized (sounds) {
                sounds.add(SoundEvent.getAssetMap().getIndex("Voice_" + s));
            }
        });
        //yby, yznb rira
        ui.setHudData("playAnySound", (Consumer<String>) (s) -> {

            synchronized (sounds) {
                sounds.add(SoundEvent.getAssetMap().getIndex(s));
            }
        });
        ui.setHudData("setLogic", (BiConsumer<String, String>) (k, v) -> {
            world.execute(() -> {
                if (k.equals("level.TopLevel")) {
                        var pageManager = store.getComponent(ref, Player.getComponentType()).getPageManager();
                        pageManager.openCustomPage(ref, store, new WinPage(store.getComponent(ref, PlayerRef.getComponentType())));
                } else store.getResource(LogicResource.Companion.getTYPE()).initValue(k, v);
            });
        });
    }

    public void onTick(@NotNull CommandBuffer<EntityStore> commands, Ref<EntityStore> ref, float dt) {
        LevelManager levelManager = commands.getResource(LevelManager.Companion.getTYPE());
        Player player = commands.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = commands.getComponent(ref, PlayerRef.getComponentType());
        TurretComponent turret = commands.getComponent(ref, TurretComponent.INSTANCE.getTYPE());
        DieselPlayerComponent dieselPlayer = commands.getComponent(ref, DieselPlayerComponent.Companion.getTYPE());
        EntityStatMap entityStatMapComponent = commands.getComponent(ref, EntityStatMap.getComponentType());
        EntityStatValue healthValue = entityStatMapComponent.get(DefaultEntityStatTypes.getHealth());
        InteractionManager interactionManager = commands.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
        int dashCharges;

        synchronized (sounds) {
            for (int s : sounds) {
                SoundUtil.playLocalPlayerSoundEvent(
                        playerRef,
                        s,
                        0,
                        SoundCategory.SFX
                );
            }
            sounds.clear();
        }

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
        String clazz = turret != null ? "turret" : dieselPlayer.getPlayerClass().toString();

        ui.setHudData("dashes", dashCharges);
        ui.setHudData("class", clazz);
        ui.setHudData("hotbarIdx", slot > 1 ? slot > 6 ? 0 : 1 : slot);
        switch (clazz){
            case "turret":
                ui.setHudData("ammo", entityStatMapComponent.get("Turret_AA_Ammo").get());
                break;
            default:
                ui.setHudData("ammo", entityStatMapComponent.get("Shotgun_Scout_Ammo").get());
        }
        ui.setHudData("health", healthValue.asPercentage());

        var sim = commands.getResource(AirSimulator.Companion.getTYPE());


        //progress bars are progress1 and progress 2
        //ui.setHudData("progress2",foo)
        if (sim.getFlying()) {
            ui.setHudData("progress1", sim.getShipHealth());
            ui.setHudData("progress1Label","Ship Health");
        } else ui.setHudData("progress1Label", "");
        ui.setHudData("progress1",0.5);
        ui.setHudData("progress1Label","");
        ui.setHudData("progress2",0.6);
        ui.setHudData("progress2Label","");
        //"currentLevel" for current level
    }

    public void showMessage(@NotNull String chain) {
        ui.setHudData("chain", chain);
    }

    public void die() {
        ui.closeHud();
    }
}
