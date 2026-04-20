package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class PlaneComponent : Component<EntityStore?> {
    var health = 0

    override fun clone(): Component<EntityStore?>? = PlaneComponent().also {
        it.health = health
    }
    companion object {
        val CODEC = buildCodec(::PlaneComponent) {
            addField("Health", Codec.INTEGER) {
                getter { health }
                setter { health = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(PlaneComponent::class.java) }
    }
}