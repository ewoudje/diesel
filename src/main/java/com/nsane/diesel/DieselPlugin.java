package com.nsane.diesel;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Resource;
import com.hypixel.hytale.component.ResourceType;
import com.hypixel.hytale.component.event.EntityEventType;
import com.hypixel.hytale.component.event.WorldEventType;
import com.hypixel.hytale.component.system.EcsEvent;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.boss.RiseRockInteraction;
import com.nsane.diesel.boss.RisenRockComponent;
import com.nsane.diesel.boss.RisenRockRefSystem;
import com.nsane.diesel.boss.RisenRockTickSystem;
import com.nsane.diesel.commands.ExampleCommand;
import com.nsane.diesel.commands.OpenMyUiCommand;
import com.nsane.diesel.events.ExampleEvent;
import com.nsane.diesel.flying.AirSimulator;
import com.nsane.diesel.flying.CloudCommand;
import com.nsane.diesel.flying.CloudComponent;
import com.nsane.diesel.flying.CloudRefSystem;
import com.nsane.diesel.flying.CloudTickSystem;
import com.nsane.diesel.flying.FlyingCommand;
import com.nsane.diesel.flying.HelicopterComponent;
import com.nsane.diesel.flying.HelicopterRefSystem;
import com.nsane.diesel.flying.HelicopterTickSystem;
import com.nsane.diesel.flying.PlaneComponent;
import com.nsane.diesel.flying.PlaneRefSystem;
import com.nsane.diesel.flying.PlaneTickSystem;
import com.nsane.diesel.flying.SimulatedTransformationSystem;
import com.nsane.diesel.flying.SimulatedTransformComponent;
import com.nsane.diesel.flying.SimulationSystem;
import com.nsane.diesel.interactions.ApplyMovementConfigInteraction;
import com.nsane.diesel.level.ChangeLevelEvent;
import com.nsane.diesel.level.LevelCommand;
import com.nsane.diesel.level.LevelManager;
import com.nsane.diesel.level.LevelSystem;
import com.nsane.diesel.level.PartOfLevelComponent;
import com.nsane.diesel.level.spawner.NPCSpawner;
import com.nsane.diesel.level.spawner.NPCSpawnerRefSystem;
import com.nsane.diesel.level.spawner.NPCSpawnerSpawnSystem;
import com.nsane.diesel.logic.LogicComponentTracker;
import com.nsane.diesel.logic.OpenLogicUIInteraction;
import com.nsane.diesel.logic.bool_computer.BoolComputer;
import com.nsane.diesel.logic.bool_computer.BoolComputerSystem;
import com.nsane.diesel.logic.state_reader.StateReader;
import com.nsane.diesel.logic.state_reader.StateReaderSystem;
import com.nsane.diesel.logic.state_writer.StateWriter;
import com.nsane.diesel.logic.state_writer.StateWriterSystem;
import com.nsane.diesel.player.DieselPlayerComponent;
import com.nsane.diesel.player.DieselPlayerSystem;
import com.nsane.diesel.player.DieselResource;
import com.nsane.diesel.player.PlayerReviveSystem;
import com.nsane.diesel.player.ReviveInteraction;
import com.nsane.diesel.projectiles.DieselProjectileComponent;
import com.nsane.diesel.projectiles.DieselProjectileSystem;
import com.nsane.diesel.projectiles.DieselProjectileType;
import com.nsane.diesel.projectiles.DieselShootInteraction;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import li.kelp.vuetale.javascript.ModuleRegistry;


import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class DieselPlugin extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.get("Diesel Experience");
    private static DieselPlugin instance;
    private final Map<Class<?>, Object> registeredTypes = new HashMap<>();

    public DieselPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        instance = this;

        // in setup
        try {
            PluginBase vuetale = PluginManager.get().getPlugin(PluginIdentifier.fromString("kelp.li:Vuetale"));
            ClassLoader cl = vuetale.getClass().getClassLoader();
            Class<?> reg = Class.forName("li.kelp.vuetale.javascript.ModuleRegistry", true, cl);
            Object instance = reg.getField("INSTANCE").get(null);
            reg.getMethod("registerModule", String.class, Class.class)
                    .invoke(instance, "diesel", DieselPlugin.class);
        } catch (Exception e) {}


        HytaleAssetStore.Builder<String, DieselProjectileType, DefaultAssetMap<String, DieselProjectileType>> builder =
                HytaleAssetStore.builder(DieselProjectileType.class, new DefaultAssetMap<>());
        builder.setCodec(DieselProjectileType.Companion.getCODEC());
        builder.setPath("Diesel/Projectiles");
        builder.setKeyFunction(DieselProjectileType::getId);
        builder.setReplaceOnRemove(i -> new DieselProjectileType());
        builder.loadsAfter(SoundEvent.class, ParticleSystem.class, ModelAsset.class, ProjectileConfig.class);
        builder.loadsBefore(RootInteraction.class);

        AssetRegistry.register(builder.build());

        registerChunkComponent(StateReader.class, "StateReader", StateReader.Companion.getCODEC());
        registerChunkComponent(StateWriter.class, "StateWriter", StateWriter.Companion.getCODEC());
        registerChunkComponent(BoolComputer.class, "BoolComputer", BoolComputer.Companion.getCODEC());
        registerChunkComponent(NPCSpawner.class, "NPCSpawner", NPCSpawner.Companion.getCODEC());

        registerEntityComponent(RisenRockComponent.class, "RisenRockComponent", RisenRockComponent.Companion.getCODEC());
        registerEntityComponent(DieselPlayerComponent.class, "DieselPlayerComponent", DieselPlayerComponent.Companion.getCODEC());
        registerEntityComponent(DieselProjectileComponent.class, "DieselProjectileComponent", DieselProjectileComponent.Companion.getCODEC());
        registerEntityComponent(SimulatedTransformComponent.class, "SimulatedInAir", SimulatedTransformComponent.Companion.getCODEC());
        registerEntityComponent(CloudComponent.class, "Cloud", CloudComponent.Companion.getCODEC());
        registerEntityComponent(PlaneComponent.class, "Plane", PlaneComponent.Companion.getCODEC());
        registerEntityComponent(HelicopterComponent.class, "Helicopter", HelicopterComponent.Companion.getCODEC());
        registerEntityComponent(PartOfLevelComponent.class, "PartOfLevel", PartOfLevelComponent.INSTANCE.getCODEC());
        registerEntityResource(DieselResource.class, "DieselPlayersResource", DieselResource.Companion.getCODEC());
        registerEntityResource(AirSimulator.class, "AirSimulator", AirSimulator.Companion.getCODEC());
        registerEntityResource(LevelManager.class, "LevelManager", LevelManager.Companion.getCODEC());

        registerWorldEvent(ChangeLevelEvent.class);

        getCodecRegistry(Interaction.CODEC)
                .register("OpenLogicUI", OpenLogicUIInteraction.class, OpenLogicUIInteraction.Companion.getCODEC())
                .register("RiseRock", RiseRockInteraction.class, RiseRockInteraction.Companion.getCODEC())
                .register("DieselShoot", DieselShootInteraction.class, DieselShootInteraction.Companion.getCODEC())
                .register("ApplyMovementConfig", ApplyMovementConfigInteraction.class,ApplyMovementConfigInteraction.CODEC)
                .register("Revive", ReviveInteraction.class, ReviveInteraction.Companion.getCODEC());

        getCommandRegistry().registerCommand(new OpenMyUiCommand());
        getCommandRegistry().registerCommand(new FlyingCommand());
        getCommandRegistry().registerCommand(new CloudCommand());
        getCommandRegistry().registerCommand(new LevelCommand());

        getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
        getEventRegistry().registerGlobal(PlayerReadyEvent.class, DieselPlayerSystem::playerReadyEvent);

        ModuleRegistry.INSTANCE.registerModule("diesel", DieselPlugin.class,null);

        LOGGER.atInfo().log("Setup complete!!!");
    }

    @Override
    protected void start() {
        getEntityStoreRegistry().registerSystem(SimulatedTransformationSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(CloudTickSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(CloudRefSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(PlaneTickSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(PlaneRefSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(HelicopterTickSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(HelicopterRefSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(SimulationSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(SimulationSystem.OnLevelChange.INSTANCE);
        getEntityStoreRegistry().registerSystem(RisenRockTickSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(RisenRockRefSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(DieselProjectileSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(LevelSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(LevelSystem.RemoveLevelEntities.INSTANCE);
        getEntityStoreRegistry().registerSystem(LevelSystem.TrackLevelEntities.INSTANCE);
        getEntityStoreRegistry().registerSystem(PlayerReviveSystem.INSTANCE);

        getChunkStoreRegistry().registerSystem(NPCSpawnerRefSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(NPCSpawnerSpawnSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(StateReaderSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(StateWriterSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(BoolComputerSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(LogicComponentTracker.INSTANCE);
    }

    public static <C extends Component<STORE>, STORE> ComponentType<STORE, C> getComponent(@Nonnull Class<C> componentClass) {
        return (ComponentType<STORE, C>) instance.registeredTypes.get(componentClass);
    }

    public static <C extends Resource<STORE>, STORE> ResourceType<STORE, C> getResource(@Nonnull Class<C> resourceClass) {
        return (ResourceType<STORE, C>) instance.registeredTypes.get(resourceClass);
    }

    public static  <C extends EcsEvent> Pair<WorldEventType<EntityStore, C>, WorldEventType<ChunkStore, C>> getEvent(@NotNull Class<C> clazz) {
        return (Pair<WorldEventType<EntityStore, C>, WorldEventType<ChunkStore, C>>) instance.registeredTypes.get(clazz);
    }

    @Override
    protected void shutdown() {
        //TODO LogicComponentTracker.INSTANCE.clear();
    }

    private <C extends Component<ChunkStore>> void registerChunkComponent(
            Class<? super C> clazz,
            String name,
            BuilderCodec<C> codec
    ) {
        registeredTypes.put(clazz, getChunkStoreRegistry().registerComponent(clazz, name, codec));
    }

    private <C extends Component<EntityStore>> void registerEntityComponent(
            Class<? super C> clazz,
            String name,
            BuilderCodec<C> codec
    ) {
        registeredTypes.put(clazz, getEntityStoreRegistry().registerComponent(clazz, name, codec));
    }

    private <R extends Resource<EntityStore>> void registerEntityResource(
            Class<? super R> clazz,
            String name,
            BuilderCodec<R> codec
    ) {
        registeredTypes.put(clazz, getEntityStoreRegistry().registerResource(clazz, name, codec));
    }

    private <R extends EcsEvent> void registerWorldEvent(
            Class<? super R> clazz
    ) {
        var entityEvent = getEntityStoreRegistry().registerWorldEventType(clazz);
        var chunkEvent = getChunkStoreRegistry().registerWorldEventType(clazz);
        registeredTypes.put(clazz, new Pair<>(entityEvent, chunkEvent));
    }
}