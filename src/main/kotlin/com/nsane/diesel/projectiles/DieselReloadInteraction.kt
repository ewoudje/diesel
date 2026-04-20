package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.inventory.ItemStack
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction
import com.nsane.diesel.player.DieselPlayerComponent
import kotlin.collections.set

class DieselReloadInteraction: SimpleInteraction() {
    var magazineId: String? = null
    var magazineSize: Int = 1
    var duration: Float = 1.0f

    override fun tick0(
        firstRun: Boolean,
        dt: Float,
        type: InteractionType,
        ctx: InteractionContext,
        cooldownHandler: CooldownHandler
    ) {
        val buffer = ctx.commandBuffer!!
        val playerComp = buffer.getComponent(ctx.entity, DieselPlayerComponent.TYPE)

        if (magazineId == null || playerComp == null) {
            ctx.state.state = InteractionState.Failed
            return
        }

        ctx.state.progress += dt / duration
        if (ctx.state.progress >= 1.0) {
            playerComp.ammo[magazineId!!] = magazineSize
            ctx.heldItem = ItemStack(ctx.heldItem!!.item.getItemIdForState("Loaded")!!)
            ctx.heldItemContainer!!.setItemStackForSlot(ctx.heldItemSlot.toShort(), ctx.heldItem!!)
            ctx.state.state = InteractionState.ItemChanged
        } else ctx.state.state = InteractionState.NotFinished

        super.tick0(firstRun, dt, type, ctx, cooldownHandler)
    }

    override fun simulateTick0(
        firstRun: Boolean,
        dt: Float,
        type: InteractionType,
        ctx: InteractionContext,
        cooldownHandler: CooldownHandler
    ) {
        ctx.state.progress += dt / duration
        if (ctx.state.progress >= 1.0) {
            ctx.heldItem = ItemStack(ctx.heldItem!!.item.getItemIdForState("Loaded")!!)
            ctx.state.state = InteractionState.ItemChanged
        } else ctx.state.state = InteractionState.NotFinished
        super.simulateTick0(firstRun, dt, type, ctx, cooldownHandler)
    }

    companion object {
        val CODEC = BuilderCodec.builder(
            DieselReloadInteraction::class.java,
            ::DieselReloadInteraction,
            SimpleInteraction.CODEC
        )
            .appendInherited(
                KeyedCodec<Int>("MagazineSize", Codec.INTEGER),
                { self: DieselReloadInteraction, i: Int -> self.magazineSize = i },
                { self: DieselReloadInteraction -> self.magazineSize },
                { o, p -> o.magazineSize = p.magazineSize }
            )
            .add()
            .appendInherited(
                KeyedCodec<String>("MagazineId", Codec.STRING),
                { self: DieselReloadInteraction, i: String -> self.magazineId = i },
                { self: DieselReloadInteraction -> self.magazineId },
                { o, p -> o.magazineId = p.magazineId }
            )
            .add()
            .appendInherited(
                KeyedCodec<Float>("Duration", Codec.FLOAT),
                { self: DieselReloadInteraction, i: Float -> self.duration = i },
                { self: DieselReloadInteraction -> self.duration },
                { o, p -> o.duration = p.duration }
            )
            .add()
            .build()
    }
}