package com.nsane.diesel.player

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselPlayerComponent: Component<EntityStore?> {
    override fun clone(): Component<EntityStore?> = DieselPlayerComponent()

    companion object {
        val CODEC = buildCodec(::DieselPlayerComponent) {

        }

        val TYPE by lazy { DieselPlugin.getComponent(DieselPlayerComponent::class.java) }
    }
}
