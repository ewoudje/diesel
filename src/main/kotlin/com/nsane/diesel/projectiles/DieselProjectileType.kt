package com.nsane.diesel.projectiles

import com.hypixel.hytale.assetstore.AssetExtraInfo
import com.hypixel.hytale.assetstore.AssetKeyValidator
import com.hypixel.hytale.assetstore.AssetRegistry
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec
import com.hypixel.hytale.assetstore.map.DefaultAssetMap
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap
import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.codec.codecs.array.ArrayCodec
import com.hypixel.hytale.codec.validation.ValidatorCache
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageCalculator
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageEffects
import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig
import io.github.hytalekt.kytale.codec.buildCodec

data class DieselProjectileType(
    private var id_: String? = null,
    var data: AssetExtraInfo.Data? = null,
    var modelKey: String? = null,
    var configKey: String? = null,
    var damageEffects: DamageEffects? = null,
    var damageCalculator: DamageCalculator? = null,
    var blockHitParticles: Array<WorldParticle>? = null,
    var projectileCount: Int = 1,
    var spreadAmount: Double = 1.0,
    var bulletSpeed: Double = 25.0,
    var bulletDistance: Double = 100.0,
    ) : JsonAssetWithMap<String, DefaultAssetMap<String, DieselProjectileType>> {
    val model get() = ModelAsset.getAssetMap().getAsset(modelKey) ?: throw Exception("Model $modelKey not found")

    override fun getId(): String? = id_

    companion object {
        val VALIDATOR = ValidatorCache(AssetKeyValidator(::ASSET_STORE))
        val ABSTRACT_CODEC = buildCodec(::DieselProjectileType) {
            addField("Model", Codec.STRING) {
                getter { modelKey }
                setter { modelKey = it }
                addValidator(ModelAsset.VALIDATOR_CACHE.validator)
            }

            addField("Config", Codec.STRING) {
                getter { configKey }
                setter { configKey = it }
                addValidator(ProjectileConfig.VALIDATOR_CACHE.validator)
            }

            addField("DamageEffects", DamageEffects.CODEC) {
                getter { damageEffects }
                setter { damageEffects = it }
            }

            addField("DamageCalculator", DamageCalculator.CODEC) {
                getter { damageCalculator }
                setter { damageCalculator = it }
            }

            addField("BlockHitParticles", ArrayCodec(WorldParticle.CODEC, { Array<WorldParticle?>(it) { null } })) {
                getter { blockHitParticles }
                setter { blockHitParticles = it }
            }

            addField("ProjectileCount", Codec.INTEGER) {
                getter { projectileCount }
                setter { projectileCount = it }
            }

            addField("SpreadAmount", Codec.DOUBLE) {
                getter { spreadAmount }
                setter { spreadAmount = it }
            }

            addField("BulletSpeed", Codec.DOUBLE) {
                getter { bulletSpeed }
                setter { bulletSpeed = it }
            }

            addField("BulletDistance", Codec.DOUBLE) {
                getter { bulletDistance }
                setter { bulletDistance = it }
            }
        }

        val CODEC = AssetBuilderCodec.wrap(
            ABSTRACT_CODEC,
            Codec.STRING,
            { t, k -> t?.id_ = k },
            { t -> t?.id },
            { t, data -> t?.data = data },
            { t -> t?.data })

        val ASSET_STORE by lazy {
            AssetRegistry.getAssetStore<String, DieselProjectileType, DefaultAssetMap<String, DieselProjectileType>>(DieselProjectileType::class.java)!!
        }

        val ASSET_MAP by lazy { ASSET_STORE.assetMap!! }
    }
}