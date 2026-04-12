package com.nsane.diesel.boss

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

class RisenRockComponent : Component<EntityStore?> {
    override fun clone(): Component<EntityStore?>? = RisenRockComponent()
}