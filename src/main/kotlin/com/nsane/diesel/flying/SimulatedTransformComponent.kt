package com.nsane.diesel.flying

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec

class SimulatedTransformComponent : Component<EntityStore?> {
    val position: Vector3d = Vector3d()
    val rotation: Vector3f = Vector3f()
    val velocity: Vector3d = Vector3d()
    val omega: Vector3f = Vector3f()

    override fun clone(): Component<EntityStore?>? = SimulatedTransformComponent().also {
        it.position.assign(this.position)
        it.rotation.assign(this.rotation)
        it.velocity.assign(this.velocity)
        it.omega.assign(this.omega)
    }

    companion object {
        val CODEC = buildCodec(::SimulatedTransformComponent) {
            addField("Position", Vector3d.CODEC) {
                getter { position }
                setter { position.assign(it) }
            }

            addField("Rotation", Vector3f.CODEC) {
                getter { rotation }
                setter { rotation.assign(it) }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(SimulatedTransformComponent::class.java) }
    }
}