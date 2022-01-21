package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;

public class HardDelete implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {

        } else {
            if (args.length != 1) {
                Histeria.log("Wrong command syntax", LogType.ERROR);
                return false;
            }
            executeDelete(args[0]);

        }
        return true;

    }

    private void executeDelete(String name) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            try {

                MySQL sql = Histeria.mySQL;
                ResultSet result = sql.query("SELECT `uuid` FROM `Player` WHERE `name` = '" + name + "';");

                String uuid = null;
                if (result.next())
                    uuid = result.getString("uuid");

                if (uuid == null) {
                    Histeria.log("Player unknown in data base", LogType.ERROR);
                    return;
                }
                System.out.println(uuid);
                SyncServer server = new SyncServer(uuid, Request.DELETE);
                server.execute();
                Histeria.log("Inventory delete task done", LogType.INFO);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
