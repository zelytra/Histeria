/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Mute;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UnMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length < 1) {
            LangMessage.sendMessage((Player) sender, "command.wrongSyntax");
            return false;
        }


        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            MySQL mySQL = Histeria.mySQL;
            ResultSet resultSet = mySQL.query("SELECT * FROM `Mute` WHERE `name` = '" + args[0] + "' ;");

            try {

                if (!resultSet.next()) {
                    LangMessage.sendMessage((Player) sender, "command.playerNotExist");
                    return;
                }

                CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(args[0]);
                Mute mute;
                if (customPlayer != null) {
                    mute = customPlayer.getMute();
                } else {
                    mute = new Mute(resultSet.getLong("startTime"),
                            resultSet.getLong("time"),
                            resultSet.getString("reason"),
                            resultSet.getString("staff"));
                }

                resultSet.close();

                if (!mute.isMute()) {
                    LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§c" + args[0], "command.playerNotMute", "");
                } else {
                    mute.unMute();
                    mySQL.update("UPDATE `Mute` SET `name` = '" + args[0] +
                            "' ,`startTime` = " + mute.getStartTime() +
                            " ,`time` = " + mute.getTime() +
                            " ,`reason` = '" + mute.getReason() +
                            "' ,`staff` = '" + mute.getStaffName() + "' WHERE `name` = '" + args[0] + "' ;");

                    LangMessage.sendMessage((Player) sender, Message.PLAYER_PREFIX.getMsg() + "§a" + args[0], "command.playerUnMute", "");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        return true;
    }

}
