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
import fr.zelytra.histeria.managers.visual.chat.GroupFX;
import fr.zelytra.histeria.managers.visual.tab.Tab;
import net.kyori.adventure.text.Component;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerJoinVisual implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text().content("§7[§a+§7] " + e.getPlayer().getName()).build());

        e.getPlayer().sendPlayerListHeader(Component.text().content(Tab.HEADER.toString()).build());
        updateFX(e.getPlayer());
        e.getPlayer().sendPlayerListFooter(Component.text().content(Tab.FOOTER.toString()).build());

    }


    public static void onParentChange(UserDataRecalculateEvent e) {
        Player player = Bukkit.getPlayer(Objects.requireNonNull(e.getUser().getUsername()));

        if (player != null)
            updateFX(player);

    }

    private static void updateFX(Player player) {
        player.setScoreboard(Histeria.visualTeamManager.getScoreboard());

        Histeria.visualTeamManager.getTeamOf(player).removeEntry(player.getName());
        GroupFX groupFX = GroupFX.getByName(Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup());
        Histeria.visualTeamManager.getTeamOf(groupFX).addEntry(player.getName());

        player.playerListName(Component.text()
                .content(" " + groupFX.getBadge().toString())
                .append(Component.text().content(" " + player.getName()).color(groupFX.getNameColor()))
                .build());
    }


}
