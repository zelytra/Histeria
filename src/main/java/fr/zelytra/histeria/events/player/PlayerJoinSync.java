/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.serverSynchro.PacketReceiver;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinSync implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            PacketReceiver packetReceiver = new PacketReceiver(e.getPlayer());
            packetReceiver.requestPlayer();
            long time = System.currentTimeMillis();
            while (!packetReceiver.receiveFinish()) {
                if (System.currentTimeMillis() - time >= 2000) {
                    Bukkit.getScheduler().runTask(Histeria.getInstance(),()->e.getPlayer().kickPlayer("§cFailed to sync inventory please contact an admin"));
                    return;
                }
                if (packetReceiver.isNew()) {
                    return;
                }
            }
            packetReceiver.buildPlayer();
        });


    }
}
