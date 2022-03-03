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
import fr.zelytra.histeria.commands.vanish.Vanish;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinSync implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!Histeria.synchro) return;
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            SyncServer server = new SyncServer(e.getPlayer(), Request.GET);
            server.execute();

            long time = System.currentTimeMillis();
            while (!server.isCommunicationOver) {
                if (System.currentTimeMillis() - time >= 2000) {
                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> e.getPlayer().kickPlayer("§cFailed to sync inventory please contact an admin"));
                    return;
                }

                if (server.isNewPlayer) return;
            }

            if (!Vanish.isVanished(e.getPlayer()))
                Bukkit.broadcast(Component.text().content("§7[§a+§7] " + e.getPlayer().getName()).build());

        });

    }
}
