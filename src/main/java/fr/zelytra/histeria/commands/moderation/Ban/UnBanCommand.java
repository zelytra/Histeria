/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Ban;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UnBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length < 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            MySQL mySQL = Histeria.mySQL;
            ResultSet resultSet = mySQL.query("SELECT * FROM `Ban` WHERE `name` = '" + args[0] + "' ;");

            try {

                if (!resultSet.next()) {
                    LangMessage.sendMessage((Player) sender, "command.playerNotExist");
                    return;
                }

                Ban ban = new Ban(resultSet.getLong("startTime"),
                        resultSet.getLong("time"),
                        resultSet.getString("reason"),
                        resultSet.getString("staff"));

                resultSet.close();

                if (!ban.isBan()) {
                    LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§c" + args[0], "command.playerNotBan", "");
                } else {
                    ban.unBan();
                    mySQL.update("UPDATE `Ban` SET `name` = '" + args[0] +
                            "' ,`startTime` = " + ban.getStartTime() +
                            " ,`time` = " + ban.getTime() +
                            " ,`reason` = '" + ban.getReason() +
                            "' ,`staff` = '" + ban.getStaffName() + "' WHERE `name` = '" + args[0] + "' ;");

                    LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§a" + args[0], "command.playerUnBan", "");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        return true;
    }

}
