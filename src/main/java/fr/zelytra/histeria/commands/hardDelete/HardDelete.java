package fr.zelytra.histeria.commands.hardDelete;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
import fr.zelytra.histeria.utils.Message;
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

            if (args.length != 1) {
                sender.sendMessage(Message.PLAYER_PREFIX + "§cWrong command syntax");
                return false;
            }

            if (Bukkit.getPlayer(args[0]) != null) {
                sender.sendMessage(Message.PLAYER_PREFIX + "§cYou cannot execute this command while this player is online");
                return false;
            }

            sender.sendMessage(Message.PLAYER_PREFIX + "§eStarting task...");

        } else {

            if (args.length != 1) {
                Histeria.log("Wrong command syntax", LogType.ERROR);
                return false;
            }

            if (Bukkit.getPlayer(args[0]) != null) {
                Histeria.log("You cannot execute this command while this player is online", LogType.INFO);
                return false;
            }

        }

        executeDelete(args[0]);
        sender.sendMessage(Message.PLAYER_PREFIX + "§eDeleting task done");
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
                    new DiscordLog(WebHookType.CHEATER, "**" + name + "** has not been found in database for Hard deletion");
                    return;
                }

                SyncServer server = new SyncServer(uuid, Request.DELETE);
                server.execute();

                new DiscordLog(WebHookType.CHEATER, "**" + name + "** data's has been deleted");
                Histeria.log("Inventory delete task done", LogType.INFO);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
