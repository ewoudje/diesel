package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselProjectileComponent : Component<EntityStore?> {
    var type: String? = null
    var owner: Ref<EntityStore?>? = null

    override fun clone(): Component<EntityStore?>? = DieselProjectileComponent()

    companion object {
        val CODEC = buildCodec(::DieselProjectileComponent) {
            addField("Type", Codec.STRING) {
                getter { type }
                setter { type = it }
                addValidator(DieselProjectileType.VALIDATOR.validator)
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselProjectileComponent::class.java) }
    }
}