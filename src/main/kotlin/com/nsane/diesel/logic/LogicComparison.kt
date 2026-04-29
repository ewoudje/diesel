package com.nsane.diesel.logic

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.codecs.EnumCodec
import io.github.hytalekt.kytale.codec.CodecBuilder
import java.util.EnumSet

enum class LogicComparison {
    EQUAL,
    NOT_EQUAL,
    GREATER,
    LESSER,
    GREATER_OR_EQUAL,
    LESS_OR_EQUAL;

    fun between(value1: String, value2: String): Boolean = when (this) {
        EQUAL -> value1 == value2
        NOT_EQUAL -> value1 != value2
        GREATER -> (value1.toDoubleOrNull() ?: Double.NaN) > (value2.toDoubleOrNull() ?: Double.NaN)
        LESSER -> (value1.toDoubleOrNull() ?: Double.NaN) < (value2.toDoubleOrNull() ?: Double.NaN)
        GREATER_OR_EQUAL -> (value1.toDoubleOrNull() ?: Double.NaN) >= (value2.toDoubleOrNull() ?: Double.NaN)
        LESS_OR_EQUAL -> (value1.toDoubleOrNull() ?: Double.NaN) <= (value2.toDoubleOrNull() ?: Double.NaN)
    }

    companion object {
        val CODEC = EnumCodec(LogicComparison::class.java, EnumCodec.EnumStyle.LEGACY)
    }
}