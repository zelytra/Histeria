package fr.zelytra.histeria.commands.hardDelete;

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

public class HardDeleteTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers())
                commandsList.add(player.getName());

            try {
                MySQL sql = Histeria.mySQL;
                ResultSet result = sql.query("SELECT `name` FROM `Player` ;");

                while (result.next())
                    commandsList.add(result.getString("name"));


                commandsList = Utils.dynamicTab(commandsList, args[0]);


            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return commandsList;
    }
}
