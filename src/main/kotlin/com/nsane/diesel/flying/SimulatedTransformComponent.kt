package com.nsane.diesel.flying

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.DieselPlugin
import io.github.hytalekt.kytale.codec.buildCodec
import io.github.hytalekt.kytale.ext.minus

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

    fun setWithWorldPosition(sim: AirSimulator, vector: Vector3d) =
        position.assign((vector - sim.worldInShipPosition)
            .rotateZ(sim.shipRotation.z)
            .rotateY(sim.shipRotation.y)
            .rotateX(sim.shipRotation.x)
            .add(sim.shipPosition))

    fun setWithWorldVelocity(sim: AirSimulator, vector: Vector3d) =
        velocity.assign(vector
            .rotateZ(sim.shipRotation.z)
            .rotateY(sim.shipRotation.y)
            .rotateX(sim.shipRotation.x)
            .add(sim.shipVelocity))


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

            addField("Velocity", Vector3d.CODEC) {
                getter { velocity }
                setter { velocity.assign(it) }
            }

            addField("Omega", Vector3f.CODEC) {
                getter { omega }
                setter { omega.assign(it) }
            }
        }

        val TYPE by lazy { DieselPlugin.getComponent(SimulatedTransformComponent::class.java) }
    }
}