package com.nsane.diesel.player

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.packets.interface_.HudComponent
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class DieselCommand : AbstractPlayerCommand("diesel", "") {
    override fun execute(
        ctx: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        playerRef: PlayerRef,
        var5: World
    ) {
        val comp = store.getComponent(ref, DieselPlayerComponent.TYPE)!!
        comp.disable = !comp.disable
        val hudManager = store.getComponent(ref, Player.getComponentType())!!.hudManager
        if (comp.disable) {

            comp.hud?.die()
            hudManager.setVisibleHudComponents(
                playerRef,
                HudComponent.UtilitySlotSelector,
                HudComponent.BlockVariantSelector,
                HudComponent.StatusIcons,
                HudComponent.Hotbar,
                HudComponent.Chat,
                HudComponent.Notifications,
                HudComponent.KillFeed,
                HudComponent.InputBindings,
                HudComponent.Reticle,
                HudComponent.Compass,
                HudComponent.Speedometer,
                HudComponent.ObjectivePanel,
                HudComponent.PortalPanel,
                HudComponent.EventTitle,
                HudComponent.Stamina,
                HudComponent.AmmoIndicator,
                HudComponent.Health,
                HudComponent.Mana,
                HudComponent.Oxygen,
                HudComponent.BuilderToolsLegend,
                HudComponent.Sleep
            )
        } else {
            hudManager.setVisibleHudComponents(playerRef, HudComponent.Reticle)
            comp.hud = DieselHud(store, ref)
        }
    }

}