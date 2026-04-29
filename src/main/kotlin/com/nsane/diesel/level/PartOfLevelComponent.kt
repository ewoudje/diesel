package com.nsane.diesel.level

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

object PartOfLevelComponent : Component<EntityStore?> {
    override fun clone(): Component<EntityStore?>? = PartOfLevelComponent

    val CODEC = buildCodec({ PartOfLevelComponent }) {

    }

    val TYPE by lazy { DieselPlugin.getComponent(PartOfLevelComponent::class.java) }
}