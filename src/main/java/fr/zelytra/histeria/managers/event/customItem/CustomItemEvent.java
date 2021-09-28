/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.event.customItem;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomItemEvent extends Event implements Cancellable {

    private final CustomMaterial customMaterial;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private boolean isCancelled;

    public CustomItemEvent(CustomMaterial material, Player player) {
        this.customMaterial = material;
        this.player = player;
    }

    public CustomMaterial getCustomMaterial() {
        return customMaterial;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
