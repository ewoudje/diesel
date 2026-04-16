package com.nsane.diesel.player

import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselPlayersResource: Resource<EntityStore?> {
    override fun clone(): Resource<EntityStore?>? = DieselPlayersResource()

    companion object {
        val CODEC = buildCodec(::DieselPlayersResource) {

        }

        val TYPE by lazy { DieselPlugin.getResource(DieselPlayersResource::class.java) }
    }
}