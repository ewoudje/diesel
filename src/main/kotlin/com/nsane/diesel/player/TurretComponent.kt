package com.nsane.diesel.player

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

object TurretComponent: Component<EntityStore?> {
    override fun clone(): Component<EntityStore?>? = TurretComponent

    val CODEC = buildCodec({TurretComponent}) {

    }

    val TYPE by lazy { DieselPlugin.getComponent(TurretComponent::class.java) }
}