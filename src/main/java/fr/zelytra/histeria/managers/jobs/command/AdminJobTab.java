/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.command;

import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminJobTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {

            commandsList.add("info");
            commandsList.add("setLevel");
            commandsList = Utils.dynamicTab(commandsList, args[0]);

        } else if (args.length == 2) {

            for (Player player : Bukkit.getOnlinePlayers())
                commandsList.add(player.getName());
            commandsList = Utils.dynamicTab(commandsList, args[1]);

        } else if (args.length == 3 && args[0].equalsIgnoreCase("setLevel")) {

            for (JobType jobType : JobType.values())
                commandsList.add(jobType.name());

            commandsList = Utils.dynamicTab(commandsList, args[2]);

        }
        return commandsList;
    }
}
