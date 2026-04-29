package com.nsane.diesel.logic

import com.nsane.diesel.logic.state_reader.StateReader
import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore

interface LogicComponent<T> : Component<T> {
    val id: String
    var registered: Boolean

    fun getAsString(): String
    fun logicUI(playerRef: PlayerRef, selfRef: Ref<ChunkStore?>): CustomUIPage
}