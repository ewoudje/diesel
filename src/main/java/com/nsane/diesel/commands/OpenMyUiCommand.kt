package com.nsane.diesel.commands

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.player.DieselPlayerComponent
import com.nsane.diesel.player.DieselPlayerComponent.Companion.TYPE
import com.nsane.diesel.player.DieselResource
import javax.annotation.Nonnull


class OpenMyUiCommand : AbstractPlayerCommand("dieselmessage", "test msg") {
    private val message: OptionalArg<String> = withOptionalArg("message", "message", ArgTypes.STRING)
    private val actor: OptionalArg<String> = withOptionalArg("actor", "actor", ArgTypes.STRING)
    private val delay: OptionalArg<Int> = withOptionalArg("delay", "word delay", ArgTypes.INTEGER)

    protected override fun execute(
        @Nonnull commandContext: CommandContext,
        @Nonnull store: Store<EntityStore?>,
        @Nonnull ref: Ref<EntityStore?>,
        @Nonnull playerRef: PlayerRef,
        @Nonnull world: World
    ) {
        System.out.println(message.get(commandContext))

        val diesel = store.getResource(DieselResource.TYPE)

        //store.getComponent(playerRef,DieselPlayerComponent.TYPE.T)
    //diesel.broadcastMessage(store, DieselActor.FRED,message.get(commandContext),5f)
        /*val ui = PlayerUiManager.openHud(
            playerRef,
            ref as Ref<EntityStore>,
            store as Store<EntityStore>,
            "diesel",
            "DieselHud"
        )

        ui.setHudData("test", message.get(commandContext))*/
        //ui.setHudData("test2", Abc("abc", 123))

    }
}