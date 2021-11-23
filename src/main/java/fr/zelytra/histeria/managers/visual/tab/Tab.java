/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.visual.tab;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.visual.chat.GroupFX;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Tab {
    HEADER("§6§m                                        \n", "§6§l<   §r§bHisteria   §6§l>\n"),
    FOOTER("\n", " §e[§642§e/§6100§e] | Server: §6Faction§e | §7" + Histeria.version + " \n", "\n", "§bhisteria.fr\n", "§6§m                                        ");

    private String[] content;
    private static List<Player> updateingPlayer = new ArrayList<>();

    Tab(String... content) {
        this.content = content;
    }

    public static void updateFooterForAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendPlayerListFooter(Component.text().content(Tab.FOOTER.toString()).build());
        }
    }

    private String[] footerBuilder() {
        String[] content = new String[5];
        content[0] = "\n";
        content[1] = " §e[§6" + Histeria.server.getPlayerCount() + "§e/§6100§e] | Server: §6" + Histeria.server.getServerName() + "§e | §7v2.0 \n";
        content[2] = "\n";
        content[3] = "§bhisteria.fr\n";
        content[4] = "§6§m                                        ";
        return content;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this != FOOTER)
            for (String line : content)
                stringBuilder.append(line);
        else
            for (String line : footerBuilder())
                stringBuilder.append(line);

        return stringBuilder.toString();
    }

    public static void updateTab(Player player) {
        if (updateingPlayer.contains(player)) return;
        updateingPlayer.add(player);
        player.setScoreboard(Histeria.visualTeamManager.getScoreboard());
        player.sendPlayerListHeader(Component.text().content(Tab.HEADER.toString()).build());
        player.sendPlayerListFooter(Component.text().content(Tab.FOOTER.toString()).build());
        Histeria.visualTeamManager.getTeamOf(player).removeEntry(player.getName());
        GroupFX groupFX = GroupFX.getByName(Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup());
        Histeria.visualTeamManager.getTeamOf(groupFX).addEntry(player.getName());
        player.playerListName(Component.text()
                .content(" " + groupFX.getBadge().toString())
                .append(Component.text().content(" " + player.getName()).color(groupFX.getNameColor()))
                .build());
        updateingPlayer.remove(player);
    }

}
