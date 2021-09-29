/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.broadcast;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Broadcast implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return false;

        String message = "§c";
        for (int x = 1; x < args.length; x++) {
            message += args[x] + " ";
        }
        Player player = (Player) sender;

        if (args.length < 2) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        if (args[0].equalsIgnoreCase("all")) {

            BaseComponent text = new TextComponent(Message.HISTALERT.getMsg() + message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);
                p.sendActionBar(text);
                player.sendTitle("", message, 5, 50, 5);
            }


            Bukkit.broadcastMessage(Message.HISTALERT.getMsg() + message);


        } else if (args[0].equalsIgnoreCase("msg")) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Message.HISTALERT.getMsg() + message);
                p.playSound(p.getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);
            }


        } else if (args[0].equalsIgnoreCase("title")) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                player.sendTitle("", message, 5, 50, 5);
                p.playSound(p.getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);
            }

        } else if (args[0].equalsIgnoreCase("hotbar")) {

            BaseComponent text = new TextComponent(Message.HISTALERT.getMsg() + message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendActionBar(text);
                p.playSound(p.getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);
            }


        }
        return true;
    }
}
