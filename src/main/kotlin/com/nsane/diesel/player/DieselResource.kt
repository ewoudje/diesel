package com.nsane.diesel.player

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.KeyedCodec
import com.hypixel.hytale.codec.builder.BuilderCodec
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.CodecBuilder
import io.github.hytalekt.kytale.codec.buildCodec
import java.util.function.BiConsumer

class DieselResource: Resource<EntityStore?> {
    val respawnPoint = Vector3d()
    var deadLevel: String? = null
    var globalRespawnTimer = Double.NaN
    fun broadcastMessage(accessor: ComponentAccessor<EntityStore?>, chain: String) {
        val world = accessor.externalData.world
        world.execute {
            world.playerRefs.forEach {
                val player = accessor.getComponent(it.reference!!, DieselPlayerComponent.TYPE)
                player?.showMessage(chain)
            }
        }
    }

    override fun clone(): Resource<EntityStore?>? = DieselResource().also {
        it.respawnPoint.assign(respawnPoint)
        it.deadLevel = deadLevel
        it.globalRespawnTimer = globalRespawnTimer
    }

    companion object {
        val CODEC = BuilderCodec.builder(DieselResource::class.java, ::DieselResource)
            /*
            .addField(
                   KeyedCodec("RespawnPoint", Vector3d.CODEC),
                { t: DieselResource, u: Vector3d? -> t.respawnPoint.assign(u ?: Vector3d()) },
                { t: DieselResource -> t.respawnPoint }
            )
            .addField(
                KeyedCodec("DeadLevel", Codec.STRING),
                { t: DieselResource, u: String? -> t.deadLevel = u },
                { t: DieselResource -> t.deadLevel }
            )
            .addField(
                KeyedCodec("GlobalRespawnTimer", Codec.DOUBLE),
                { t: DieselResource, u: Double? -> t.globalRespawnTimer = u ?: Double.NaN },
                { t: DieselResource -> t.globalRespawnTimer }
            )*/
            .build()

        val TYPE by lazy { DieselPlugin.getResource(DieselResource::class.java) }
    }
}