package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction
import com.nsane.diesel.flying.stage.DeathStarRace

class MoveShipInteraction : SimpleInstantInteraction() {
    var left = false
    override fun firstRun(
        var1: InteractionType,
        context: InteractionContext,
        var3: CooldownHandler
    ) {
        context.state.state = InteractionState.Failed
        val sim = context.commandBuffer?.getResource(AirSimulator.TYPE) ?: return
        val stage = sim.stage
        if (stage !is DeathStarRace) return
        if (!stage.isInLane) return

        when {
            left && stage.lane <= 0 -> return
            !left && stage.lane >= 2 -> return
            left -> stage.lane--
            !left -> stage.lane++
        }

        context.state.state = InteractionState.Finished
    }

    companion object {
        val CODEC = BuilderCodec.builder(MoveShipInteraction::class.java, ::MoveShipInteraction, SimpleInteraction.CODEC)
            .appendInherited(
                KeyedCodec<Boolean>("Left", Codec.BOOLEAN),
                { self: MoveShipInteraction, i: Boolean -> self.left = i },
                { self: MoveShipInteraction -> self.left },
                { o, p -> o.left = p.left }
            )
            .add()
            .build()
    }
}