/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.stats;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.timer.TimeFormater;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopStats implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length != 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        if (args[0].equalsIgnoreCase("kill")) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                ResultSet result = Histeria.mySQL.query("SELECT `name`,`kill`  FROM `Player` ORDER BY `kill` DESC;");
                LangMessage.sendMessage((Player) sender, "", "command.stats", "");
                try {

                    for (int x = 0; x < 10; x++)
                        if (result.next())
                            sender.sendMessage("§8● §6" + result.getString("name") + " §8: §6" + result.getInt("kill"));

                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });

        } else if (args[0].equalsIgnoreCase("death")) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                ResultSet result = Histeria.mySQL.query("SELECT `name`,`death`  FROM `Player` ORDER BY `death` DESC;");
                LangMessage.sendMessage((Player) sender, "", "command.stats", "");
                try {

                    for (int x = 0; x < 10; x++)
                        if (result.next())
                            sender.sendMessage("§8● §6" + result.getString("name") + " §8: §6" + result.getInt("death"));

                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });

        } else if (args[0].equalsIgnoreCase("timePlayed")) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                ResultSet result = Histeria.mySQL.query("SELECT `name`,`playTime`  FROM `Player` ORDER BY `playTime` DESC;");
                LangMessage.sendMessage((Player) sender, "", "command.stats", "");
                try {

                    for (int x = 0; x < 10; x++)
                        if (result.next())
                            sender.sendMessage("§8● §6" + result.getString("name") + " §8: §6" + TimeFormater.display(result.getInt("playTime")));

                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });


        } else if (args[0].equalsIgnoreCase("bank")) {

            Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

                ResultSet result = Histeria.mySQL.query("SELECT `name`,`money`  FROM `Bank` ORDER BY `money` DESC;");
                LangMessage.sendMessage((Player) sender, "", "command.stats", "");
                try {

                    for (int x = 0; x < 10; x++)
                        if (result.next())
                            sender.sendMessage("§8● §6" + result.getString("name") + " §8: §6" + result.getInt("money") + Emote.GOLD);

                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });

        }else{
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }
        return true;
    }

}


