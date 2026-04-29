package com.nsane.diesel.flying.enviroment

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.ComponentAccessor
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.nsane.diesel.flying.AirSimulator
import kotlin.random.Random


class DeathStarEnvironment() : SimpleEnvironment(70) {
    val blockSize = 80
    val cityWidth = 4
    var lastZ = 0.0

    override fun tick(accessor: ComponentAccessor<EntityStore?>, dt: Float) {
        super.tick(accessor, dt)
        val sim = accessor.getResource(AirSimulator.TYPE)
        val offset = 10

        if (sim.shipPosition.z - lastZ > blockSize) {
            lastZ = sim.shipPosition.z
            var i = cityWidth / -2
            while (i <= cityWidth / 2) {
                val rand = Random.nextInt(1, 5)
                val o = if (i < 0) -offset else offset
                val x = blockSize * i + (blockSize / 2) + o
                if (i++ == 0) continue

                val holder = EntityStore.REGISTRY.newHolder()
                holder.ensureComponent(EntityStore.REGISTRY.nonSerializedComponentType)
                populateEnvironmentalHolder(
                    "City",
                    holder,
                    sim,
                    "City$rand",
                    2.1f,
                    Vector3d(x.toDouble(), 150.0, sim.shipPosition.z + 200.0),
                    Vector3f(),
                    Vector3d(),
                    Vector3f(),
                )

                accessor.addEntity(holder, AddReason.SPAWN)
            }
        }
    }
}
