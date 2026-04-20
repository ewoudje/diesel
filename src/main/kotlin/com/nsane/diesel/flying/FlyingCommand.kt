package com.nsane.diesel.flying

import com.hypixel.hytale.component.Store
import com.hypixel.hytale.math.util.ChunkUtil
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class FlyingCommand : AbstractWorldCommand("flying", "flying") {
    private val x: OptionalArg<Double> = withOptionalArg("x", "Position of ship", ArgTypes.DOUBLE)
    private val y: OptionalArg<Double> = withOptionalArg("y", "Position of ship", ArgTypes.DOUBLE)
    private val z: OptionalArg<Double> = withOptionalArg("z", "Position of ship", ArgTypes.DOUBLE)
    private val pitch: OptionalArg<Float> = withOptionalArg("pitch", "Rotation of ship", ArgTypes.FLOAT)
    private val yaw: OptionalArg<Float> = withOptionalArg("yaw", "Rotation of ship", ArgTypes.FLOAT)
    private val roll: OptionalArg<Float> = withOptionalArg("roll", "Rotation of ship", ArgTypes.FLOAT)

    private val speedModifier: OptionalArg<Double> = withOptionalArg("speedModifier", "SpeedModifier of ship", ArgTypes.DOUBLE)

    override fun execute(
        ctx: CommandContext,
        world: World,
        store: Store<EntityStore?>
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        x.get(ctx)?.let { sim.shipPosition.x = it }
        y.get(ctx)?.let { sim.shipPosition.y = it }
        z.get(ctx)?.let { sim.shipPosition.z = it }
        pitch.get(ctx)?.let { sim.shipRotation.pitch = it }
        yaw.get(ctx)?.let { sim.shipRotation.yaw = it }
        roll.get(ctx)?.let { sim.shipRotation.roll = it }
        speedModifier.get(ctx)?.let { sim.velocityModifier = it }
        sim.flying = true
        ctx.sendMessage(Message.raw("Done"))
    }
}