package com.nsane.diesel;

import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.window.ClientOpenWindow;
import com.hypixel.hytale.protocol.packets.window.CloseWindow;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.nsane.diesel.player.DieselPlayerComponent;

import java.util.HashMap;
import java.util.Map;

public class DieselPacketFilter implements PlayerPacketFilter {
    private final Map<PlayerRef, DieselPlayerComponent> componentMap = new HashMap<>();

    private boolean isDisabled(PlayerRef player) {
        DieselPlayerComponent comp;
        synchronized (componentMap) {
            comp = componentMap.get(player);
        }

        if (comp != null && comp.getDisable()) return true;
        if (comp == null) {
            var r = player.getReference();
            if (r != null) r.getStore().getExternalData().getWorld().execute(() -> {
                var foundComp = r.getStore().getComponent(r, DieselPlayerComponent.Companion.getTYPE());
                synchronized (componentMap) {
                    componentMap.putIfAbsent(player, foundComp);
                }
            });
        }

        return false;
    }

    @Override
    public boolean test(PlayerRef player, Packet packet) {
        if (isDisabled(player)) return false;
        if (packet instanceof ClientOpenWindow) {
            player.getPacketHandler().writeNoCache(new CloseWindow(0));
            return true;
        }

        return false;
    }
}
