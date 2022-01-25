/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.events;

import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JobLevelUpEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private final CustomPlayer player;
    private final JobInterface jobInterface;

    public JobLevelUpEvent(CustomPlayer player, JobInterface jobInterface) {

        this.player = player;
        this.jobInterface = jobInterface;

    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public CustomPlayer getPlayer() {
        return player;
    }

    public JobInterface getJob() {
        return jobInterface;
    }
}
