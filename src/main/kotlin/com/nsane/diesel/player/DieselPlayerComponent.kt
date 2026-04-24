package com.nsane.diesel.player

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselActor
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselPlayerComponent: Component<EntityStore?> {
    var playerClass: PlayerClass? = null
    var hud: DieselHud? = null

    fun showMessage(actor: DieselActor, text: String, duration: Float = 4f) {
        hud!!.showMessage(actor, text, duration)
    }

    override fun clone(): Component<EntityStore?> = DieselPlayerComponent()

    companion object {
        val CODEC = buildCodec(::DieselPlayerComponent) {
            addField("PlayerClass", PlayerClass.CODEC) {
                getter { playerClass }
                setter { playerClass = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselPlayerComponent::class.java) }
    }
}
