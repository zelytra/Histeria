/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameMode implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player target;
            boolean isOtherPlayer = false;
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    LangMessage.sendMessage((Player) sender, "command.playerOffLine");
                    return false;
                }
                target = Bukkit.getPlayer(args[0]);
                isOtherPlayer = true;
            } else {
                target = (Player) sender;
            }
            assert target != null;
            switch (command.getName()) {
                case "gmc":
                    target.setGameMode(org.bukkit.GameMode.CREATIVE);
                    LangMessage.sendMessage(target, "miscellaneous.gmc");
                    if (isOtherPlayer)
                        LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX + "§a" + target.getName(), "miscellaneous.gmcPlayer", "");
                    break;
                case "gms":
                    target.setGameMode(org.bukkit.GameMode.SURVIVAL);
                    LangMessage.sendMessage(target, "miscellaneous.gms");
                    if (isOtherPlayer)
                        LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX + "§a" + target.getName(), "miscellaneous.gmsPlayer", "");
                    break;
                case "gmsc":
                    target.setGameMode(org.bukkit.GameMode.SPECTATOR);
                    LangMessage.sendMessage(target, "miscellaneous.gmsc");
                    if (isOtherPlayer)
                        LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX + "§a" + target.getName(), "miscellaneous.gmscPlayer", "");
                    break;
                default:
                    return false;
            }

            return true;
        }
        return false;

    }
}
