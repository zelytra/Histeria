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
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.managers.server.PMessage;
import fr.zelytra.histeria.managers.server.SubChannel;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import fr.zelytra.histeria.managers.visual.tab.Tab;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftVisual implements Listener {

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent e) {
        if (!SwitchServer.getPlayerSwitching().contains(e.getPlayer())) {
            e.quitMessage(Component.text().content("§7[§c-§7] " + e.getPlayer().getName()).build());
            new PMessage(SubChannel.PLAYER_COUNT, null, new String[]{"ALL"});
            Tab.updateTab(e.getPlayer());
            new DiscordLog(WebHookType.SERVER, e.getPlayer().getName() + " left server " + Histeria.server.getServerName());
        } else {
            if (!Vanish.isVanished(e.getPlayer()))
                e.quitMessage(Component.text().content("§7[§b◯§7] " + e.getPlayer().getName()).build());
            else
                e.quitMessage(Component.text().build());
            new DiscordLog(WebHookType.SERVER, e.getPlayer().getName() + " switch server");
        }
    }


}
