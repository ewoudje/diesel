package com.nsane.diesel.projectiles

import com.hypixel.hytale.codec.codecs.EnumCodec
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset

enum class DieselProjectileType(
    val modelKey: String,
    val projectileCount: Int,
    val spreadAmount: Double,
    val bulletDamage: Float,
    val bulletSpeed: Double = 25.0,
    val bulletDistance: Double = 100.0,
) {
    SCOUT_GUN("Projectile_AA_Asset", 10, 7.0,  2.0f, 40.0, 8.0),
    AA("Projectile_AA_Asset", 1, 2.0, 10.0f),
    FLAK("Projectile_AA_Asset", 1, 0.5, 50.0f);

    val model get() = ModelAsset.getAssetMap().getAsset(modelKey) ?: throw Exception("Model $modelKey not found")

    companion object {
        val CODEC = EnumCodec(DieselProjectileType::class.java)
    }
}