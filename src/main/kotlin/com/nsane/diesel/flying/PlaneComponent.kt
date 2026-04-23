package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.random.Random

class PlaneComponent : Component<EntityStore?> {
    var timeSinceLastBullet = 0f
    var flyingAway: Float = 10000.0f
    val target: Vector3d = Vector3d(Random.nextDouble() * 5 - 2.5, 0.0, Random.nextDouble() * 5 - 2.5)

    override fun clone(): Component<EntityStore?>? = PlaneComponent().also {
        it.target.assign(this.target)
        it.flyingAway = flyingAway
        it.timeSinceLastBullet = timeSinceLastBullet
    }

    companion object {
        val CODEC = buildCodec(::PlaneComponent) {

        }

        val TYPE by lazy { DieselPlugin.getComponent(PlaneComponent::class.java) }
    }
}