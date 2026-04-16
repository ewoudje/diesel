package com.nsane.diesel.boss

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class RisenRockComponent : Component<EntityStore?> {
    var lifetime = 0

    override fun clone(): Component<EntityStore?>? = RisenRockComponent().also {
        it.lifetime = lifetime
    }

    companion object {
        val CODEC = buildCodec(::RisenRockComponent) {
            addField("Lifetime", Codec.INTEGER) {
                getter { lifetime }
                setter { lifetime = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(RisenRockComponent::class.java) }
    }
}