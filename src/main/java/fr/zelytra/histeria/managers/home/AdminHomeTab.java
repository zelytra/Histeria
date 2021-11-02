package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminHomeTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers())
                commandsList.add(player.getName());

            try {
                MySQL sql = Histeria.mySQL;
                ResultSet result = sql.query("SELECT DISTINCT `uuid` FROM `Home` ;");

                while (result.next()) {
                    String name = Bukkit.getOfflinePlayer(UUID.fromString(result.getString("uuid"))).getName();
                    if (name == null) continue;
                    commandsList.add(name);
                }

                commandsList = Utils.dynamicTab(commandsList, args[0]);


            } catch (SQLException exception) {
                exception.printStackTrace();
            }

        } else if (args.length == 2) {
            CustomPlayer customTarget = new CustomPlayer(args[0]);
            if (!customTarget.hasPlayedBefore()) return commandsList;

            for (Home home : customTarget.getHomes())
                commandsList.add(home.getName());

            commandsList = Utils.dynamicTab(commandsList, args[1]);

        }

        return commandsList;
    }
}
