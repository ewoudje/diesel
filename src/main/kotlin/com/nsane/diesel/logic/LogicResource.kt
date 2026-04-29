package com.nsane.diesel.logic

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.codecs.map.MapCodec
import com.nsane.diesel.logic.state_reader.StateReader
import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.RemoveReason
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.math.vector.Vector3i
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.level.LevelManager
import com.nsane.diesel.logic.bool_computer.BoolComputer
import com.nsane.diesel.logic.pressure_plate.PressurePlate
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.collections.set

class LogicResource(
    private var refs: MutableMap<String, String> = HashMap()
): Resource<EntityStore?> {
    fun getValue(id: String): String? = refs[id]

    fun idChanged(id: String, newId: String) {
        val v = refs.remove(id)
        v?.let { refs[newId] = it }
    }

    fun initValue(id: String, value: String) {
        refs[id] = value
    }

    fun valueChanged(id: String, value: String) {
        refs[id] = value
    }

    override fun clone(): Resource<EntityStore?> = LogicResource(refs.toMutableMap())


    companion object {
        val CODEC = buildCodec(::LogicResource) {
            addField("Refs", MapCodec(Codec.STRING, ::HashMap)) {
                getter { refs }
                setter { refs = it.toMutableMap() }
            }
        }

        val TYPE by lazy { DieselPlugin.getResource(LogicResource::class.java) }
    }
}