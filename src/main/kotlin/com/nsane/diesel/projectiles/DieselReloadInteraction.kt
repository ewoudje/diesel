package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.protocol.InteractionState
import com.hypixel.hytale.protocol.InteractionType
import com.hypixel.hytale.server.core.entity.InteractionContext
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction
import com.nsane.diesel.player.DieselPlayerComponent
import kotlin.collections.set

class DieselReloadInteraction: SimpleInstantInteraction() {
    var magazineId: String? = null
    var magazineSize: Int = 1

    override fun firstRun(
        type: InteractionType,
        ctx: InteractionContext,
        p2: CooldownHandler
    ) {
        val buffer = ctx.commandBuffer!!
        val playerComp = buffer.getComponent(ctx.entity, DieselPlayerComponent.TYPE)

        if (magazineId != null && playerComp != null) {
            playerComp.ammo[magazineId!!] = magazineSize
            ctx.state.state = InteractionState.Finished
        } else ctx.state.state = InteractionState.Failed
    }

    companion object {
        val CODEC = BuilderCodec.builder(
            DieselReloadInteraction::class.java,
            ::DieselReloadInteraction,
            SimpleInstantInteraction.CODEC
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
            .build()
    }
}