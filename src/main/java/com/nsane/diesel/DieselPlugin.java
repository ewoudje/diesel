package com.nsane.diesel;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.nsane.diesel.commands.ExampleCommand;
import com.nsane.diesel.events.ExampleEvent;
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
    private final Map<Class<?>, ComponentType<?, ?>> components = new HashMap<>();

    public DieselPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        instance = this;

        registerChunkComponent(StateReader.class, "StateReader", StateReader.Companion.getCODEC());
        registerChunkComponent(StateWriter.class, "StateWriter", StateWriter.Companion.getCODEC());
        registerChunkComponent(BoolComputer.class, "BoolComputer", BoolComputer.Companion.getCODEC());
        getCodecRegistry(Interaction.CODEC).register("OpenLogicUI", OpenLogicUIInteraction.class, OpenLogicUIInteraction.Companion.getCODEC());

        getChunkStoreRegistry().registerSystem(StateReaderSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(StateWriterSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(BoolComputerSystem.INSTANCE);
        getChunkStoreRegistry().registerSystem(LogicComponentTracker.INSTANCE);

        getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);

        LOGGER.atInfo().log("Setup complete!!!");
    }

    public static <C extends Component<ChunkStore>> ComponentType<ChunkStore, C> getChunkComponent(@Nonnull Class<C> componentClass) {
        return (ComponentType<ChunkStore, C>) instance.components.get(componentClass);
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
        components.put(clazz, getChunkStoreRegistry().registerComponent(clazz, name, codec));
    }
}