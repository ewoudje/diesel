package com.nsane.diesel.flying.stage

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.Holder
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.CloudComponent
import com.nsane.diesel.flying.SimulatedTransformComponent
import io.github.hytalekt.kytale.ext.plus
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class BossStage : Stage() {
    val bossPosition = Vector3d(0.0, -20.0, 0.0)
    lateinit var boss: Ref<EntityStore?>

    var circling = 0.0f

    override fun tick(store: Store<EntityStore?>, sim: AirSimulator, dt: Float) {
        circling = PhysicsMath.normalizeAngle((Math.PI * dt * (SHIP_RPM / 30)).toFloat() + circling)
        val newPos = Vector3d(
            bossPosition.x + (sin(circling) * BOSS_RADI),
            bossPosition.y + 30.0,
            bossPosition.z + (cos(circling) * BOSS_RADI)
        )
        val traveled = newPos.clone().subtract(sim.shipPosition)
        sim.shipVelocity.assign(traveled)
        sim.distanceTraveled += traveled.length()

        sim.shipPosition.assign(newPos)
        sim.shipRotation.yaw = (circling + (PI / 2.0)).toFloat()
    }

    override fun setup(
        store: Store<EntityStore?>,
        sim: AirSimulator,
        oldStage: Stage?
    ) {
        spawnBoss(store)
    }

    private fun spawnBoss(store: Store<EntityStore?>) {
        val modelAsset = ModelAsset.getAssetMap().getAsset("Mech") ?: throw NullPointerException("Skyboss asset not found")
        val model = Model.createScaledModel(modelAsset, 10f)
        val holder = EntityStore.REGISTRY.newHolder()

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent())
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            position.assign(bossPosition)
        })
        holder.addComponent(NetworkId.getComponentType(), NetworkId(store.getExternalData().takeNextNetworkId()))
        holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
        holder.ensureComponent(UUIDComponent.getComponentType())

        boss = store.addEntity(holder, AddReason.SPAWN)!!
    }

    companion object {
        const val SHIP_RPM = 0.5
        const val BOSS_RADI = 70.0
    }
}