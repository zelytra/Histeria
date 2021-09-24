/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.listener;

import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandLogger implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        new DiscordLog(WebHookType.COMMANDS, e.getPlayer().getName() + " > " + e.getMessage());
    }
}
