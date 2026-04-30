package com.nsane.diesel.logic

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.codecs.map.MapCodec
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class LogicResource(
    private var refs: MutableMap<String, String> = HashMap()
) : Resource<EntityStore?> {
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

    fun clear() {
        refs.clear()
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