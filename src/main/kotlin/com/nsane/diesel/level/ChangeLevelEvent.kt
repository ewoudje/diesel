package com.nsane.diesel.level

import com.hypixel.hytale.component.system.CancellableEcsEvent
import com.nsane.diesel.DieselPlugin

data class ChangeLevelEvent(val oldLevel: Level?, val newLevel: Level) : CancellableEcsEvent() {
    companion object {
        private val TYPES by lazy { DieselPlugin.getEvent(ChangeLevelEvent::class.java) }
        val ENTITY_TYPE = TYPES.first
        val CHUNK_TYPE = TYPES.second
    }
}
