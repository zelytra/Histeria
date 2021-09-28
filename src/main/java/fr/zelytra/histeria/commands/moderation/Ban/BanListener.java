/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Ban;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(AsyncPlayerPreLoginEvent e) {

        MySQL mySQL = Histeria.mySQL;
        try {
            ResultSet resultSet = mySQL.query("SELECT * FROM `Ban` WHERE `uuid` = '" + e.getUniqueId() + "' ;");

            if (resultSet.next()) {
                Ban ban = new Ban(resultSet.getLong("startTime"),
                        resultSet.getLong("time"),
                        resultSet.getString("reason"),
                        resultSet.getString("staff"));
                resultSet.close();

                if (ban.isBan()) {

                    long remainTime = ban.getTime() - ((System.currentTimeMillis() - ban.getStartTime()) / 1000);

                    long nextInS = remainTime;

                    long days = (nextInS / 86400);

                    long hours = (nextInS % 86400) / 3600;

                    long minutes = (nextInS % 3600) / 60;

                    long seconds = (nextInS % 60);

                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Message.HISTBAN.getMsg()
                            + "§c" + Lang.EN.get("command.playerBan")
                            + "§6" + ban.getReason()
                            + "§c " + Lang.EN.get("command.playerBanTime") + "§6" + days + "d" + hours + "h" + minutes + "m" + seconds + "s");

                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}
