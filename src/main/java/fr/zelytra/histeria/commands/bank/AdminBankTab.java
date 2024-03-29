package fr.zelytra.histeria.commands.bank;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.mysql.MySQL;
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

public class AdminBankTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> commandsList = new ArrayList<>();
        if (args.length == 1) {
            commandsList.add("take");
            commandsList.add("give");
            commandsList.add("set");
            commandsList = Utils.dynamicTab(commandsList, args[0]);

        } else if (args.length == 2) {

            for (Player player : Bukkit.getOnlinePlayers())
                commandsList.add(player.getName());

            MySQL mySQL = Histeria.mySQL;
            ResultSet resultSet = mySQL.query("SELECT `name` FROM `Player` ;");
            try {
                while (resultSet.next())
                    commandsList.add(resultSet.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            commandsList = Utils.dynamicTab(commandsList, args[1]);

        }

        return commandsList;

    }

}
