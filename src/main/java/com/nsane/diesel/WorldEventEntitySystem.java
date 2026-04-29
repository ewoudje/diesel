package com.nsane.diesel;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.EcsEvent;
import com.hypixel.hytale.component.system.QuerySystem;
import com.hypixel.hytale.component.system.WorldEventSystem;
import org.jetbrains.annotations.NotNull;

public abstract class WorldEventEntitySystem<ECS_TYPE, E extends EcsEvent> extends WorldEventSystem<ECS_TYPE, E> implements QuerySystem<ECS_TYPE> {
    protected WorldEventEntitySystem(@NotNull Class<E> eventType) {
        super(eventType);
    }

    @Override
    public void handle(@NotNull Store<ECS_TYPE> store, @NotNull CommandBuffer<ECS_TYPE> buffer, @NotNull E event) {
        store.forEachChunk(getQuery(), (chunk, _) -> {
            for (int i = 0; i < chunk.size(); i++) {
                handle(buffer, i, chunk, event);
            }
        });
    }

    public abstract void handle(@NotNull CommandBuffer<ECS_TYPE> buffer, int idx, @NotNull ArchetypeChunk<ECS_TYPE> chunk, @NotNull E event);
}
