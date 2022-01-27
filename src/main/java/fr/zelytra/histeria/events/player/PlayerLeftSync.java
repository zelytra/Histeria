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
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftSync implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void OnPlayerLeft(PlayerQuitEvent e) {

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            if (SwitchServer.getPlayerSwitching().contains(e.getPlayer())) {
                SwitchServer.getPlayerSwitching().remove(e.getPlayer());
                CustomPlayer.getCustomPlayer(e.getPlayer().getName()).destroy();
                return;
            }

            if (Histeria.synchro) {
                SyncServer server = new SyncServer(e.getPlayer(), Request.SEND);
                server.execute();
            }

            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
            customPlayer.getAfk().setAfk(false);
            customPlayer.saveData();
            customPlayer.destroy();
        });

    }
}
