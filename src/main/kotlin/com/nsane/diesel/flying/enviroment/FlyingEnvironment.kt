package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore

interface FlyingEnvironment {
    val weather: String

    fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float)
    fun environmentalUnloaded(accessor: ComponentAccessor<EntityStore?>, ref: Ref<EntityStore?>, component: EnvironmentalComponent)
}

