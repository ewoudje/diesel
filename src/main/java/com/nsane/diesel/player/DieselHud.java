package com.nsane.diesel.player;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.DieselActor;
import com.nsane.diesel.player.DieselPlayerComponent;
import com.nsane.diesel.player.DieselPlayerSystem;
import li.kelp.vuetale.app.PlayerUi;
import li.kelp.vuetale.app.PlayerUiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.round;

public class DieselHud {
    private PlayerUi ui;

    public DieselHud(Store<EntityStore> store, Ref<EntityStore> ref) {
        ui = PlayerUiManager.INSTANCE.openHud(
                store.getComponent(ref, PlayerRef.getComponentType()),
                ref,
                store,
                "diesel",
                "DieselHud"
        );
    }


    public void onTick(@NotNull CommandBuffer<EntityStore> commands, Ref<EntityStore> ref) {
        Player player = commands.getComponent(ref, Player.getComponentType());
        DieselPlayerComponent dieselPlayer = commands.getComponent(ref, DieselPlayerComponent.Companion.getTYPE());
        EntityStatMap entityStatMapComponent = commands.getComponent(ref, EntityStatMap.getComponentType());
        EntityStatValue healthValue = entityStatMapComponent.get(DefaultEntityStatTypes.getHealth());

        /*if (dieselPlayer.getAmmo().get("Shotgun_Scout_Ammo") != null){
            ammo = dieselPlayer.getAmmo().get("Shotgun_Scout_Ammo");
        }*/


        //ui.setData("health", healthValue.asPercentage());
    }

    public void showMessage(@NotNull DieselActor actor, @NotNull String text, float duration) {

    }
}
