package com.nsane.diesel.projectiles

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselProjectileComponent : Component<EntityStore?> {
    var type: DieselProjectileType? = null

    override fun clone(): Component<EntityStore?>? = DieselProjectileComponent()

    companion object {
        val CODEC = buildCodec(::DieselProjectileComponent) {
            addField("OnHit", DieselProjectileType.CODEC) {
                getter { type }
                setter { type = it }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselProjectileComponent::class.java) }
    }
}