package com.nsane.diesel.player

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
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction

class ReviveInteraction : SimpleInteraction() {
    var startingHealth = 25f

    override fun tick0(
        firstRun: Boolean,
        time: Float,
        type: InteractionType,
        context: InteractionContext,
        cooldownHandler: CooldownHandler
    ) {
        val target = context.targetEntity
        context.state.state = InteractionState.Failed

        if (target != null) {
            val death = context.commandBuffer?.getComponent(target, DeathComponent.getComponentType())
            val stats = context.commandBuffer?.getComponent(target, EntityStatMap.getComponentType())
            if (death != null && stats != null) {
                context.commandBuffer!!.removeComponent(target, DeathComponent.getComponentType())
                context.commandBuffer!!.externalData.world.execute {
                    stats.setStatValue(DefaultEntityStatTypes.getHealth(), startingHealth)
                }
                context.state.state = InteractionState.Finished
            }
        }

        super.tick0(firstRun, time, type, context, cooldownHandler)
    }

    companion object {
        val CODEC = BuilderCodec.builder(ReviveInteraction::class.java, ::ReviveInteraction, SimpleInteraction.CODEC)
            .appendInherited(
                KeyedCodec<Float>("StartingHealth", Codec.FLOAT),
                { self: ReviveInteraction, i: Float -> self.startingHealth = i },
                { self: ReviveInteraction -> self.startingHealth },
                { o, p -> o.startingHealth = p.startingHealth }
            )
            .add()
            .build()
    }
}