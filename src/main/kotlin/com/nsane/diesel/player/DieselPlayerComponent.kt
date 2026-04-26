package com.nsane.diesel.player

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselActor
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselPlayerComponent : Component<EntityStore?> {
    var playerClass: PlayerClass? = null
    var hud: DieselHud? = null
    var disable: Boolean = false

    fun showMessage(chain: String) {
        hud!!.showMessage(chain)
    }

    override fun clone(): Component<EntityStore?> = DieselPlayerComponent()

    companion object {
        val CODEC = buildCodec(::DieselPlayerComponent) {
            addField("PlayerClass", PlayerClass.CODEC) {
                getter { playerClass }
                setter { playerClass = it }
            }

            addField("Disable", Codec.BOOLEAN) {
                getter { disable }
                setter { disable = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselPlayerComponent::class.java) }
    }
}
