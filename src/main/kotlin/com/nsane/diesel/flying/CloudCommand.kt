package com.nsane.diesel.flying

import com.hypixel.hytale.component.AddReason
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.protocol.AnimationSlot
import com.hypixel.hytale.server.core.asset.type.model.config.Model
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.entity.AnimationUtils
import com.hypixel.hytale.server.core.entity.UUIDComponent
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel
import com.hypixel.hytale.server.core.modules.entity.component.PropComponent
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import kotlin.math.abs
import kotlin.random.Random


class CloudCommand: AbstractPlayerCommand("cloud", "") {
    override fun execute(
        ctx: CommandContext,
        store: Store<EntityStore?>,
        player: Ref<EntityStore?>,
        playerRef: PlayerRef,
        world: World
    ) {
        val sim = store.getResource(AirSimulator.TYPE)
        store.addEntity(PlaneTickSystem.buildPlane(sim, store), AddReason.SPAWN)
        store.addEntity(HelicopterTickSystem.buildHelicopter(sim, store), AddReason.SPAWN)
        repeat(30) {
            store.addEntity(CloudTickSystem.buildCloud(sim), AddReason.SPAWN)
        }
    }
}