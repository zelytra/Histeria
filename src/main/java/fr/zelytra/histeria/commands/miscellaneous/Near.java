/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Near implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        List<String> playerList = new ArrayList();
        DecimalFormat dec = new DecimalFormat("#0.00");

        for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
            if (entity instanceof Player) {
                playerList.add("§b- " + entity.getName() + " §6["
                        + dec.format(
                        (Math.sqrt(Math.pow(entity.getLocation().getX() - player.getLocation().getX(), 2)
                                + Math.pow(entity.getLocation().getZ() - player.getLocation().getZ(), 2))))
                        + "bl]");
            }
        }
        player.sendMessage("§9|--------[§b"+ CustomPlayer.getCustomPlayer(player.getName()).getLang().get("command.nearTitle") +"§9]--------|");

        for (String string : playerList) {
            player.sendMessage(string);
        }
        return true;
    }
}
