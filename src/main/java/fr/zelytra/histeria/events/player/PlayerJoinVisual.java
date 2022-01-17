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
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.managers.server.PMessage;
import fr.zelytra.histeria.managers.server.SubChannel;
import fr.zelytra.histeria.managers.visual.tab.Tab;
import net.kyori.adventure.text.Component;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinVisual implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!Histeria.synchro)
            e.joinMessage(Component.text().content("§7[§a+§7] " + e.getPlayer().getName()).build());

        else
            e.joinMessage(Component.text().build());


        new PMessage(SubChannel.PLAYER_COUNT, null, new String[]{"ALL"});
        Tab.updateTab(e.getPlayer());
        new DiscordLog(WebHookType.SERVER, e.getPlayer().getName() + " join server " + Histeria.server.getServerName());
    }


    public static void onParentChange(UserDataRecalculateEvent e) {
        if (e.getUser() != null && e.getUser().getUsername() != null) {
            Player player = Bukkit.getPlayer(e.getUser().getUsername());

            if (player != null)
                Tab.updateTab(player);
        }
    }
}
