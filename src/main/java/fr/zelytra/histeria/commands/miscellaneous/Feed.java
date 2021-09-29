/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Feed implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                if (!Cooldown.cooldownCheck(player, "feedCommand")) {
                    return false;
                }

                if (!Utils.canByPass(player))
                    new Cooldown(player, 300, "feedCommand");

                player.setFoodLevel(20);
                player.setSaturation(20f);
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou have been fed.");
                return true;
            } else {
                if (Bukkit.getPlayer(args[0]) != null && Utils.canByPass(player)) {
                    Player target = Bukkit.getPlayer(args[0]);
                    assert target != null;
                    target.setFoodLevel(20);
                    target.setSaturation(20f);
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou feed " + target.getName() + ".");
                    target.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aYou have been fed.");
                    return true;
                } else if (!Utils.canByPass(player)) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou cannot feed another player.");
                    return false;
                } else {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou don't have permission to perform this command.");
                    return false;
                }
            }
            //TODO Prendre en compte le statut de pvp dans l'exec de la commande
        }
        return false;
    }
}
