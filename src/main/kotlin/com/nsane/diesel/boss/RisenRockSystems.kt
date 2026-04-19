package com.nsane.diesel.boss

import com.hypixel.hytale.codec.ExtraInfo
import com.hypixel.hytale.component.*
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.RefSystem
import com.hypixel.hytale.component.system.tick.EntityTickingSystem
import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f
import com.hypixel.hytale.protocol.ChangeVelocityType
import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent
import com.hypixel.hytale.server.core.modules.entity.damage.Damage
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId
import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.github.hytalekt.kytale.ext.minus
import org.bson.BsonDocument
import org.bson.BsonDouble
import kotlin.math.abs
import kotlin.math.floor

object RisenRockTickSystem : EntityTickingSystem<EntityStore>() {
    const val ROCK_LIFETIME = 200
    const val ROCK_RISE_LIFETIME = 5
    const val ROCK_RISE_AMOUNT = 3.0

    private val velocityConfig = VelocityConfig.CODEC.decode(BsonDocument().apply {
        this["GroundResistanceNax"] = BsonDouble(0.9)
        this["AirResistanceNax"] = BsonDouble(0.9)
        this["Threshold"] = BsonDouble(3.0)
    }, ExtraInfo())

    override fun tick(
        dt: Float,
        index: Int,
        chunk: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        commands: CommandBuffer<EntityStore?>
    ) {
        val risenRock = chunk.getComponent(index, RisenRockComponent.TYPE) ?: throw IndexOutOfBoundsException()
        val transform = chunk.getComponent(index, TransformComponent.getComponentType()) ?: throw IndexOutOfBoundsException()
        val world = store.externalData.world

        risenRock.lifetime++

        when (risenRock.lifetime) {
            in 0..ROCK_RISE_LIFETIME -> {
                transform.position.y += ROCK_RISE_AMOUNT / ROCK_RISE_LIFETIME
                damageNear(commands, world, transform.position)
            }
            in (ROCK_LIFETIME - ROCK_RISE_LIFETIME)..ROCK_LIFETIME -> transform.position.y -= ROCK_RISE_AMOUNT / ROCK_RISE_LIFETIME
            in ROCK_LIFETIME..Int.MAX_VALUE -> commands.removeEntity(chunk.getReferenceTo(index), RemoveReason.REMOVE)
        }

        if (risenRock.lifetime == ROCK_RISE_LIFETIME + 3) {
            world.setBlock(floor(transform.position.x).toInt(),transform.position.y.toInt() + 1, floor(transform.position.z).toInt(), "RockBlock")
        } else if (risenRock.lifetime == ROCK_LIFETIME - ROCK_RISE_LIFETIME) {
            world.setBlock(floor(transform.position.x).toInt(), transform.position.y.toInt() + 1, floor(transform.position.z).toInt(), "Empty")
        }
    }

    fun damageNear(commands: CommandBuffer<EntityStore?>, world: World, pos: Vector3d) {
        for (player in world.playerRefs) {
            val transform = commands.getComponent(player.reference!!, TransformComponent.getComponentType())
                ?: throw IndexOutOfBoundsException()

            val diff = transform.position - pos
            if (abs(diff.x) < 0.65 && abs(diff.y) < 2 && abs(diff.z) < 0.65) {
               dealDamage(commands, diff, transform.rotation, player.reference!!)
            }
        }
    }

    fun dealDamage(commands: CommandBuffer<EntityStore?>, diff: Vector3d, rotate: Vector3f, player: Ref<EntityStore?>) {
        val damageSource = Damage.EnvironmentSource("BossRock")
        val damage = Damage(damageSource, DamageCause.PHYSICAL!!, 20.0f)
        DamageSystems.executeDamage(player, commands, damage)

        var knockbackComponent = commands.getComponent<KnockbackComponent?>(player, KnockbackComponent.getComponentType())
        if (knockbackComponent == null) {
            knockbackComponent = KnockbackComponent()
            commands.putComponent<KnockbackComponent?>(player, KnockbackComponent.getComponentType(), knockbackComponent)
        }

        diff.y *= 0.9
        knockbackComponent.velocity = diff.setLength(10.0)
        knockbackComponent.velocityType = ChangeVelocityType.Set
        knockbackComponent.velocityConfig = velocityConfig
        knockbackComponent.duration = 0.1f
    }

    override fun getQuery(): Query<EntityStore?> = Query.and(RisenRockComponent.TYPE, TransformComponent.getComponentType())
}

object RisenRockRefSystem : RefSystem<EntityStore>() {
    override fun onEntityAdded(
        ref: Ref<EntityStore?>,
        reason: AddReason,
        store: Store<EntityStore?>,
        commands: CommandBuffer<EntityStore?>
    ) {
        if (commands.getComponent(ref, NetworkId.getComponentType()) == null)
            commands.addComponent(ref, NetworkId.getComponentType(), NetworkId(store.externalData.takeNextNetworkId()))
    }

    override fun onEntityRemove(
        p0: Ref<EntityStore?>,
        p1: RemoveReason,
        p2: Store<EntityStore?>,
        p3: CommandBuffer<EntityStore?>
    ) {

    }

    override fun getQuery(): Query<EntityStore?> = RisenRockComponent.TYPE
}