/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.worldSpawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class WorldSpawnListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(WorldSpawn.worldSpawn.getSpawn());
    }

    @EventHandler
    public void onRespawn (PlayerRespawnEvent e){
        System.out.println("trigger");
        e.setRespawnLocation(WorldSpawn.worldSpawn.getSpawn());
    }
}
