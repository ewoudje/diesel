package com.nsane.diesel.commands

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import li.kelp.vuetale.app.PlayerUiManager
import javax.annotation.Nonnull


class OpenMyUiCommand : AbstractPlayerCommand("openmyui", "Vuetale! yippie!") {
    protected override fun execute(
        @Nonnull commandContext: CommandContext,
        @Nonnull store: Store<EntityStore?>,
        @Nonnull ref: Ref<EntityStore?>,
        @Nonnull playerRef: PlayerRef,
        @Nonnull world: World
    ) {
        val ui = PlayerUiManager.openHud(
            playerRef,
            ref as Ref<EntityStore>,
            store as Store<EntityStore>,
            "diesel",
            "DieselHud"
        )

        class Abc(val a: String, val b: Int)
        ui.setHudData("test", "Hello this is a test!")
        //ui.setHudData("test2", Abc("abc", 123))

    }
}