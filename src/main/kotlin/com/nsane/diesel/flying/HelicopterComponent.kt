package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.random.Random

class HelicopterComponent : Component<EntityStore?> {
    var health = 100
    var timeSinceLastBullet = 0f
    var flyingAway: Float = 10000.0f
    var hovering = false
    var hoverHeight = 10.0
    var hoverLeft = false

    override fun clone(): Component<EntityStore?>? = HelicopterComponent().also {
        it.health = health
    }

    companion object {
        val CODEC = buildCodec(::HelicopterComponent) {
            addField("Health", Codec.INTEGER) {
                getter { health }
                setter { health = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(HelicopterComponent::class.java) }
    }
}