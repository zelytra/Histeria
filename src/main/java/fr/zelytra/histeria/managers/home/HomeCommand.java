/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import fr.zelytra.histeria.utils.Message;
import net.luckperms.api.model.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());

        if (cmd.getName().equalsIgnoreCase("home")) {

            if (args.length != 1) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            for (Home home : customPlayer.getHomes()) {
                if (home.getName().equalsIgnoreCase(args[0])) {

                    if (Histeria.server.getServerName().equalsIgnoreCase(home.getServerName())) {

                        LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "home.teleport", home.getName());
                        player.teleport(home.getLocation());
                        return true;

                    } else {
                        home.setServerRequest();
                        LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "home.serverTeleport", home.getName());
                        new SwitchServer(player).switchTo(home.getServerName());
                        return true;
                    }
                }
            }
            LangMessage.sendMessage(player, "home.notFound");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("delhome")) {

            if (args.length != 1) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            for (Home home : customPlayer.getHomes()) {
                if (home.getName().equalsIgnoreCase(args[0])) {
                    LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg() + "§6" + home.getName(), "home.delete", "");
                    customPlayer.getHomes().remove(home);
                    return true;
                }
            }
            LangMessage.sendMessage(player, "home.notFound");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("sethome")) {

            if (args.length != 1) {
                LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
                return false;
            }

            User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
            HomeLimit playerHomeLimit = HomeLimit.getHomeLimit(user.getPrimaryGroup());
            if (customPlayer.getHomes().size() >= playerHomeLimit.getHomeLimit()) {
                LangMessage.sendMessage(player, "home.reachLimit");
                return true;
            }

            for (Home home : customPlayer.getHomes()) {
                if (home.getName().equalsIgnoreCase(args[0])) {
                    LangMessage.sendMessage(player, "home.homeAlreadyExist");
                    return true;
                }
            }

            if (HGuard.getByLocation(player.getLocation()) != null) {
                LangMessage.sendMessage(player, "home.noHomeInArea");
                return true;
            }

            customPlayer.getHomes().add(new Home(customPlayer, player.getLocation(), Histeria.server.getServerName(), args[0]));
            LangMessage.sendMessage(player, "home.homeSet");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("listhome")) {
            LangMessage.sendMessage(player, "", "home.list", "");

            for (Home home : customPlayer.getHomes())
                player.sendMessage("§8● §6" + home.getName() + " §e| x:§6" + home.getLocation().getBlockX() + " §ey:§6" + home.getLocation().getBlockY() + " §ez:§6" + home.getLocation().getBlockZ());

            return true;
        }
        return false;
    }

}
