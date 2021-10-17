/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.afk;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.GroupFX;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AfkListener implements Listener {

    @EventHandler
    public void onPlayerLeaveAFK(PlayerMoveEvent e) {
        if (Math.abs(e.getTo().getX() - e.getFrom().getX()) >= 0.15 || Math.abs(e.getTo().getZ() - e.getFrom().getZ()) >= 0.15) {
            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
            if (customPlayer.wasAFK()) {
                LangMessage.broadcast("§7" + e.getPlayer().getName() + " ", "afk.noLonger", "");
                customPlayer.getAfk().setAfk(false);
                customPlayer.getAfk().setCurrentLocation(customPlayer.getPlayer().getLocation());
                customPlayer.getAfk().setLastSeenTime(System.currentTimeMillis());

                GroupFX groupFX = GroupFX.getByName(Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(customPlayer.getPlayer()).getPrimaryGroup());
                customPlayer.getPlayer().playerListName(Component.text()
                        .content(" " + groupFX.getBadge().toString())
                        .append(Component.text().content(" " + customPlayer.getPlayer().getName()).color(groupFX.getNameColor()))
                        .append(Component.text().content(customPlayer.isAFK() ? " §7[AFK]" : ""))
                        .build());
            }
        }
    }
}
