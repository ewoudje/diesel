package com.nsane.diesel.logic

import com.hypixel.hytale.codec.codecs.EnumCodec

enum class BooleanOperator {
    AND,
    OR;

    companion object {
        val CODEC = EnumCodec(BooleanOperator::class.java, EnumCodec.EnumStyle.LEGACY)
    }
}