package com.nsane.diesel;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Resource;
import com.hypixel.hytale.component.ResourceType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.nsane.diesel.boss.RisenRockComponent;
import com.nsane.diesel.commands.ExampleCommand;
import com.nsane.diesel.events.ExampleEvent;
import com.nsane.diesel.flying.AirSimulator;
import com.nsane.diesel.flying.CloudCommand;
import com.nsane.diesel.flying.FlyingCommand;
import com.nsane.diesel.flying.SimulatedTransformationSystem;
import com.nsane.diesel.flying.SimulatedPositionComponent;
import com.nsane.diesel.flying.SimulationSystem;
import com.nsane.diesel.logic.LogicComponentTracker;
import com.nsane.diesel.logic.OpenLogicUIInteraction;
import com.nsane.diesel.logic.bool_computer.BoolComputer;
import com.nsane.diesel.logic.bool_computer.BoolComputerSystem;
import com.nsane.diesel.logic.state_reader.StateReader;
import com.nsane.diesel.logic.state_reader.StateReaderSystem;
import com.nsane.diesel.logic.state_writer.StateWriter;
import com.nsane.diesel.logic.state_writer.StateWriterSystem;

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

        registerChunkComponent(StateReader.class, "StateReader", StateReader.Companion.getCODEC());
        registerChunkComponent(StateWriter.class, "StateWriter", StateWriter.Companion.getCODEC());
        registerChunkComponent(BoolComputer.class, "BoolComputer", BoolComputer.Companion.getCODEC());

        registerEntityComponent(RisenRockComponent.class, "RisenRockComponent", RisenRockComponent.Companion.getCODEC());
        registerEntityComponent(SimulatedPositionComponent.class, "SimulatedInAir", SimulatedPositionComponent.Companion.getCODEC());
        registerEntityResource(AirSimulator.class, "AirSimulator", AirSimulator.Companion.getCODEC());

        getCodecRegistry(Interaction.CODEC).register("OpenLogicUI", OpenLogicUIInteraction.class, OpenLogicUIInteraction.Companion.getCODEC());

        getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        getCommandRegistry().registerCommand(new FlyingCommand());
        getCommandRegistry().registerCommand(new CloudCommand());

        getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);

        LOGGER.atInfo().log("Setup complete!!!");
    }

    @Override
    protected void start() {
        getEntityStoreRegistry().registerSystem(SimulatedTransformationSystem.INSTANCE);
        getEntityStoreRegistry().registerSystem(SimulationSystem.INSTANCE);

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
}