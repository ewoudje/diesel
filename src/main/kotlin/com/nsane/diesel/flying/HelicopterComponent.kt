package com.nsane.diesel.flying

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class HelicopterComponent : Component<EntityStore?> {
    var timeSinceLastBullet = 0f
    var flyingAway: Float = 10000.0f
    var hovering = false
    var hoverHeight = 10.0
    var hoverLeft = false

    override fun clone(): Component<EntityStore?>? = HelicopterComponent().also {
        it.timeSinceLastBullet = timeSinceLastBullet
        it.flyingAway = flyingAway
        it.hovering = hovering
        it.hoverHeight = hoverHeight
        it.hoverLeft = hoverLeft
    }

    companion object {
        val CODEC = buildCodec(::HelicopterComponent) {

        }

        val TYPE by lazy { DieselPlugin.getComponent(HelicopterComponent::class.java) }
    }
}