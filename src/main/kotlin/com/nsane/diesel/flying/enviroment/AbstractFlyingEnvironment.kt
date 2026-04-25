package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.component.Holder
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import com.nsane.diesel.flying.SimulatedTransformComponent
import com.nsane.diesel.flying.SimulatedTransformationSystem
import kotlin.math.PI
import kotlin.random.Random

abstract class AbstractFlyingEnvironment: FlyingEnvironment {
    override val weather: String
        get() = "Default_Void"

    protected val spreaders = mutableListOf<EnvironmentalSpreading>()

    override fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float) {
        spreaders.forEach { it.tick(accessor, dt) }
    }

    override fun environmentalUnloaded(
        accessor: ComponentAccessor<EntityStore?>,
        ref: Ref<EntityStore?>,
        component: EnvironmentalComponent
    ) {}

    protected fun spawnCloud(
        accessor: ComponentAccessor<EntityStore?>,
        position: Vector3d,
        rotation: Vector3f = Vector3f(0f, PI.toFloat() / 2f, 0.0f),
        velocity: Vector3d = Vector3d(-1.0, 0.0, 0.0)
    ) {
        val sim = accessor.getResource(AirSimulator.TYPE)
        val rand = Random.nextInt(1, 5)

        val holder = EntityStore.REGISTRY.newHolder()
        populateEnvironmentalHolder("Cloud", holder, sim,
            "Cloud$rand",
            Random.nextFloat() * 5 + 3,
            position,
            rotation,
            velocity,
            Vector3f())

        accessor.addEntity(holder, AddReason.SPAWN)
    }

    protected fun populateEnvironmentalHolder(
        id: String,
        holder: Holder<EntityStore?>,
        sim: AirSimulator,
        model: String,
        scale: Float,
        position: Vector3d,
        rotation: Vector3f,
        velocity: Vector3d,
        omega: Vector3f
    ) {
        val modelAsset = ModelAsset.getAssetMap().getAsset(model) ?: throw NullPointerException("$model asset not found")
        val model = Model.createScaledModel(modelAsset, scale)

        holder.addComponent(TransformComponent.getComponentType(), TransformComponent().apply {
            this.position.assign(SimulatedTransformationSystem.getWorldPosition(sim, position))
        })
        holder.addComponent(PersistentModel.getComponentType(), PersistentModel(model.toReference()))
        holder.addComponent(ModelComponent.getComponentType(), ModelComponent(model))
        holder.addComponent(SimulatedTransformComponent.TYPE, SimulatedTransformComponent().apply {
            this.position.assign(position)
            this.rotation.assign(rotation)
            this.velocity.assign(velocity)
            this.omega.assign(omega)
        })
        holder.addComponent(EnvironmentalComponent.TYPE, EnvironmentalComponent().apply { this.id = id })
        holder.ensureComponent(UUIDComponent.getComponentType())
    }
}