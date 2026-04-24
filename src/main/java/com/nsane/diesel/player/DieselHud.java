package com.nsane.diesel.player;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.player.DieselPlayerComponent;
import com.nsane.diesel.player.DieselPlayerSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.round;

public class DieselHud extends CustomUIHud {
    private double health;
    private int ammo;
    public DieselHud(@NotNull PlayerRef playerRef) {
        super(playerRef);
    }

    public void onHotbarChange(ItemContainer.ItemContainerChangeEvent event){
        show();
    }

    @Override
    protected void build(@NotNull UICommandBuilder ui) {

        //ui.append("HUD/HUD_Diesel.ui");
        //ui.set("#Health.Value",health);
        //ui.set("#HealthLabel.Text",String.valueOf(round(health*100)));
        //ui.set("#AmmoLabel.Text",String.valueOf(ammo));
        System.out.println("building hud");


    }

    public void onTick(@NotNull CommandBuffer<EntityStore> commands){
        Player player = commands.getComponent(getPlayerRef().getReference(), Player.getComponentType());
        DieselPlayerComponent dieselPlayer = commands.getComponent(getPlayerRef().getReference(), DieselPlayerComponent.Companion.getTYPE());
        EntityStatMap entityStatMapComponent = commands.getComponent(getPlayerRef().getReference(), EntityStatMap.getComponentType());
        EntityStatValue healthValue = entityStatMapComponent.get(DefaultEntityStatTypes.getHealth());

        /*if (dieselPlayer.getAmmo().get("Shotgun_Scout_Ammo") != null){
            ammo = dieselPlayer.getAmmo().get("Shotgun_Scout_Ammo");
        }*/


        health = healthValue.asPercentage();
        show();
    }


}
