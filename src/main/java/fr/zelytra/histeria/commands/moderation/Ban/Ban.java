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
import fr.zelytra.histeria.managers.mysql.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ban {

    private final long startTime;
    private long time;
    private final String reason;
    private final String staffName;

    private String player;
    private String uuid;

    public Ban(long startTime, long time, String reason, String staffName) {
        this.startTime = startTime;
        this.time = time;
        this.reason = reason;
        this.staffName = staffName;
    }

    public Ban(String player, String playerUUID, long startTime, long time, String reason, String staffName) {
        this.startTime = startTime;
        this.time = time;
        this.reason = reason;
        this.staffName = staffName;
        this.player = player;
        this.uuid = playerUUID;
    }

    public void unBan() {
        this.time = 0;
    }

    public boolean isBan() {
        return ((System.currentTimeMillis() - this.startTime) / 1000) <= time;
    }

    public String getReason() {
        return reason;
    }

    public String getStaffName() {
        return staffName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTime() {
        return time;
    }

    public void forceDataSave() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            MySQL mySQL = Histeria.mySQL;
            ResultSet resultSet = mySQL.query("SELECT `uuid` FROM `Ban`;");

            try {
                if (!resultSet.next())
                Histeria.mySQL.update("INSERT INTO `Ban` (`uuid`,`name`,`startTime`,`time`,`reason`,`staff`) VALUES ('"
                        + this.uuid + "','"
                        + this.player + "',"
                        + this.getStartTime() + ","
                        + this.getTime() + ",'"
                        + this.getReason() + "','"
                        + this.getStaffName() + "');");
                else
                    mySQL.update("UPDATE `Ban` SET `uuid` = '" + this.uuid +
                            "' ,`name` = '" + this.player +
                            "' ,`startTime` = " + this.getStartTime() +
                            " ,`time` = " + this.getTime() +
                            " ,`reason` = '" + this.getReason() +
                            "' ,`staff` = '" + this.getStaffName() + "' WHERE `uuid` = '" + this.uuid + "' ;");
                resultSet.close();
                //test
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
