package fr.zelytra.histeria.events.pluginMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnSwitch extends Event implements Cancellable {
    private Player player;
    private String server;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public OnSwitch(Player player, String server) {
        this.player = player;
        this.server = server;
    }

    public Player getPlayer() {
        return player;
    }

    public String getServer() {
        return server;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }


}
