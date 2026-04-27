package com.nsane.diesel.player

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselActor
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class DieselResource: Resource<EntityStore?> {
    fun broadcastMessage(accessor: ComponentAccessor<EntityStore?>, chain: String) {
        val world = accessor.externalData.world
        world.execute {
            world.playerRefs.forEach {
                val player = accessor.getComponent(it.reference!!, DieselPlayerComponent.TYPE)
                player?.showMessage(chain)
            }
        }
    }

    override fun clone(): Resource<EntityStore?>? = DieselResource()

    companion object {
        val CODEC = buildCodec(::DieselResource) {

        }

        val TYPE by lazy { DieselPlugin.getResource(DieselResource::class.java) }
    }
}