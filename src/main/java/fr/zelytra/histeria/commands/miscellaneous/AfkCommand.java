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
import fr.zelytra.histeria.utils.TimeFormater;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        player.sendMessage("§8---------------§6 [ AFK ] §8---------------");

        for (CustomPlayer customPlayer : CustomPlayer.getList()) {
            if (customPlayer.isAFK()) {
                String time = TimeFormater.compareToNow(customPlayer.getAfk().getLastSeenTime());
                player.sendMessage("§8● §6" + customPlayer.getPlayer().getName() + ": " + time);
            }
        }
        return true;

    }
}
