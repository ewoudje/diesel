package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class CloudComponent : Component<EntityStore?> {
    var lifetime = 0

    override fun clone(): Component<EntityStore?>? = CloudComponent().also {
        it.lifetime = lifetime
    }
    companion object {
        val CODEC = buildCodec(::CloudComponent) {
            addField("Lifetime", Codec.INTEGER) {
                getter { lifetime }
                setter { lifetime = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(CloudComponent::class.java) }
    }
}