/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events;

import fr.zelytra.histeria.Histeria;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class ReloadingServer implements Listener {

    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().contains("/reload") || e.getMessage().contains("/rl")) {
            Histeria.isReloading = true;
        }
    }

    @EventHandler
    public void onReload(ServerCommandEvent e) {
        if (e.getCommand().contains("reload") || e.getCommand().contains("rl")) {
            Histeria.isReloading = true;
        }
    }
}
