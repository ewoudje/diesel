package com.nsane.diesel.player

import com.hypixel.hytale.codec.codecs.EnumCodec

enum class PlayerClass {
    SCOUT,
    MEDIC,
    SOLDIER,
    HEAVY;

    companion object {
        val CODEC = EnumCodec(PlayerClass::class.java)
    }
}