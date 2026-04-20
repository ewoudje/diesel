package com.nsane.diesel.player

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.codecs.map.MapCodec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselPlayerComponent: Component<EntityStore?> {
    var playerClass: PlayerClass? = null
    var ammo: MutableMap<String, Int> = mutableMapOf()

    override fun clone(): Component<EntityStore?> = DieselPlayerComponent()

    companion object {
        val CODEC = buildCodec(::DieselPlayerComponent) {
            addField("PlayerClass", PlayerClass.CODEC) {
                getter { playerClass }
                setter { playerClass = it }
            }

            addField("Ammo", MapCodec(Codec.INTEGER) { mutableMapOf<String, Int>() }) {
                getter { ammo }
                setter { ammo = it.toMutableMap() }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselPlayerComponent::class.java) }
    }
}
