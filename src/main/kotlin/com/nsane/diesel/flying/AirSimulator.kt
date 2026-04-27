package com.nsane.diesel.flying

import com.hypixel.hytale.codec.Codec
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Resource
import com.hypixel.hytale.math.shape.Box
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import com.nsane.diesel.flying.stage.Stage
import com.nsane.diesel.flying.stage.WaveStage
import com.nsane.diesel.player.DieselResource
import com.nsane.diesel.projectiles.DieselProjectileComponent
import io.github.hytalekt.kytale.codec.buildCodec
import kotlin.random.Random

class AirSimulator: Resource<EntityStore?> {

    val flying get() = stage != null
    val worldInShipPosition: Vector3d = Vector3d(0.0, 80.0, 0.0)
    val shipPosition: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipBox: Box = Box(-7.0, 77.0, -7.0, 7.0, 85.0, 7.0)
    val shipVelocity: Vector3d = Vector3d(0.0, 0.0, 0.0)
    val shipRotation: Vector3f = Vector3f(0.0f, 0.0f, 0.0f)

    var velocityModifier = 1.0
    var distanceTraveled = 0.0
    var shipHealth = 1.0
    var shipHealthState = 0
    var stage: Stage? = null
    val environmentalAmounts = mutableMapOf<String, Int>()

    fun killedPlane(accessor: ComponentAccessor<EntityStore?>) {
        if (Random.nextDouble() < 0.4) {
            val resource = accessor.getResource(DieselResource.TYPE)
            if (Random.nextDouble() < 0.3)
                resource.broadcastMessage(accessor,  "planeDownSod")
            else
                resource.broadcastMessage(accessor,  "planeDownFred")
        }

        if (stage is WaveStage)
            (stage as WaveStage).planes--
    }

    fun killedHeli(accessor: ComponentAccessor<EntityStore?>) {
        if (Random.nextDouble() < 0.3) {
            val resource = accessor.getResource(DieselResource.TYPE)
            if (Random.nextDouble() < 0.3)
                resource.broadcastMessage(accessor,  "heliDownSod")
            else
                resource.broadcastMessage(accessor,  "heliDownFred")
        }

        if (stage is WaveStage)
                (stage as WaveStage).helicopters--
    }

    override fun clone(): Resource<EntityStore?>? = AirSimulator().also {
        it.stage = stage
        it.velocityModifier = velocityModifier
        it.distanceTraveled = distanceTraveled
        it.shipHealth = shipHealth
        it.worldInShipPosition.assign(worldInShipPosition)
        it.shipPosition.assign(shipPosition)
        it.shipVelocity.assign(shipVelocity)
        it.shipRotation.assign(shipRotation)
    }

    fun bulletHitBlock(
        commands: ComponentAccessor<EntityStore?>,
        ref: Ref<EntityStore?>,
        projectile: DieselProjectileComponent
    ) {
        if (!(projectile.owner?.isValid ?: false)) return
        if (commands.getComponent(projectile.owner!!, Player.getComponentType()) != null) return
        var damage = 1.0
        if (commands.getComponent(projectile.owner!!, HelicopterComponent.TYPE) != null) damage = 0.3

        shipHealth -= (damage / 200.0)
    }

    companion object {
        val CODEC = buildCodec(::AirSimulator) {
            addField("VelocityModifier", Codec.DOUBLE) {
                getter { velocityModifier }
                setter { velocityModifier = it}
            }

            addField("DistanceTraveled", Codec.DOUBLE) {
                getter { distanceTraveled }
                setter { distanceTraveled = it}
            }

            addField("ShipHealth", Codec.DOUBLE) {
                getter { shipHealth }
                setter { shipHealth = it}
            }

            addField("ShipPosition", Vector3d.CODEC) {
                getter { shipPosition }
                setter { shipPosition.assign(it) }
            }

            addField("ShipVelocity", Vector3d.CODEC) {
                getter { shipVelocity }
                setter { shipVelocity.assign(it) }
            }

            addField("ShipRotation", Vector3f.CODEC) {
                getter { shipRotation }
                setter { shipRotation.assign(it) }
            }
        }

        val TYPE by lazy { DieselPlugin.getResource(AirSimulator::class.java) }
    }
}
