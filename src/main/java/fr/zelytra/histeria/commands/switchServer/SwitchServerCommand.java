/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.switchServer;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SwitchServerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length != 2) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;

        if (target == null && !args[0].equalsIgnoreCase("@a")) {
            LangMessage.sendMessage((Player) sender, "command.playerOffLine");
            return false;
        }

        if (!Histeria.server.getServersList().contains(args[1])) {
            LangMessage.sendMessage(player, "server.serverNotFound");
            return false;
        }

        if (args[0].equalsIgnoreCase("@a")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                new SwitchServer(p).switchTo(args[1]);
            }
        } else {
            new SwitchServer(target).switchTo(args[1]);
        }

        LangMessage.sendMessage(player, Message.PLAYER_PREFIX.toString(), "server.switchSuccess", args[1]);
        return true;
    }
}
