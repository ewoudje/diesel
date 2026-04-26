package com.nsane.diesel.logic.pressure_plate

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.logic.LogicComponent
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.random.Random

class PressurePlate(override var id: String = "") : LogicComponent<ChunkStore?> {
    var pressedIn = false

    override fun clone(): Component<ChunkStore?>? = PressurePlate().also {
        it.id = id
        it.pressedIn = pressedIn
    }

    override fun getAsBoolean(): Boolean = pressedIn
    override fun getAsString(): String = pressedIn.toString()
    override fun getAsDouble(): Double = if (pressedIn) 1.0 else 0.0

    override fun logicUI(
        playerRef: PlayerRef,
        selfRef: Ref<ChunkStore?>
    ): CustomUIPage = PressurePlatePage(playerRef, selfRef)

    companion object {
        val CODEC = buildCodec(::PressurePlate) {
            addField("LogicId", Codec.STRING) {
                documentation = "Id of the resulting logic"
                setter { id = it }
                getter { id }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(PressurePlate::class.java) }
    }
}