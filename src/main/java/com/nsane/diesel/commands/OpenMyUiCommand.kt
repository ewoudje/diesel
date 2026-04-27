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
import com.nsane.diesel.DieselActor
import com.nsane.diesel.player.DieselPlayerComponent
import com.nsane.diesel.player.DieselPlayerComponent.Companion.TYPE
import com.nsane.diesel.player.DieselResource
import li.kelp.vuetale.app.PlayerUiManager
import javax.annotation.Nonnull


class OpenMyUiCommand : AbstractPlayerCommand("dieselmessage", "test msg") {
    private val chain: OptionalArg<String> = withOptionalArg("chain", "message chain id", ArgTypes.STRING)


    protected override fun execute(
        @Nonnull commandContext: CommandContext,
        @Nonnull store: Store<EntityStore?>,
        @Nonnull ref: Ref<EntityStore?>,
        @Nonnull playerRef: PlayerRef,
        @Nonnull world: World
    ) {
        System.out.println(chain.get(commandContext))
        val diesel = store.getResource(DieselResource.TYPE)
        diesel.broadcastMessage(store, chain.get(commandContext))
    }
}