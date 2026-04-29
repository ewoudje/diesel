package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class EnvironmentalComponent : Component<EntityStore?> {
    var id: String? = null

    override fun clone(): Component<EntityStore?>? = EnvironmentalComponent().also {
        it.id = id
    }

    companion object {
        val CODEC = buildCodec(::EnvironmentalComponent) {
            addField("EnvironmentalId", Codec.STRING) {
                getter { id }
                setter { id = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(EnvironmentalComponent::class.java) }
    }
}