package com.nsane.diesel.level

import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand

import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class LevelCommand : AbstractWorldCommand("level", "") {
    val levelNameArg = withRequiredArg("name", "An level", ArgTypes.STRING)

    override fun execute(
        ctx: CommandContext,
        world: World,
        store: Store<EntityStore?>
    ) {
        val name = levelNameArg.get(ctx)
        val levelManager = store.getResource(LevelManager.TYPE)

        levelManager.enter(name)
    }
}