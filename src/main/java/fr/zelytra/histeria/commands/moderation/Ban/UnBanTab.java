/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.commands.moderation.Ban;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnBanTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();


        if (args.length == 1) {
            MySQL mySQL = Histeria.mySQL;
            ResultSet resultSet = mySQL.query("SELECT `name` FROM `Ban` ;");

            try {
                while (resultSet.next())
                    commandsList.add(resultSet.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            commandsList = Utils.dynamicTab(commandsList, args[0]);
            return commandsList;
        }


        return commandsList;
    }
}
