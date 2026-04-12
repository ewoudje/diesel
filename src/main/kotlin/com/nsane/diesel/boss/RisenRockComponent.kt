package com.nsane.diesel.boss

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class RisenRockComponent : Component<EntityStore?> {
    override fun clone(): Component<EntityStore?>? = RisenRockComponent()

    companion object {
        val CODEC = buildCodec(::RisenRockComponent) {

        }

        val TYPE by lazy { DieselPlugin.getComponent(RisenRockComponent::class.java) }
    }
}