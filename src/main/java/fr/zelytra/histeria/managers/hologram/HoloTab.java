/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.hologram;

import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HoloTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {

            commandsList.add("create");
            commandsList.add("add");
            commandsList.add("edit");
            commandsList.add("move");
            commandsList.add("delete");

            commandsList = Utils.dynamicTab(commandsList, args[0]);

        }

        if (args.length == 2) {
            for (Hologram hologram : Hologram.holograms)
                commandsList.add(hologram.getTag());

            commandsList = Utils.dynamicTab(commandsList, args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("move")) {
            commandsList.add("-c");
            commandsList = Utils.dynamicTab(commandsList, args[2]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("edit")) {

            if (Hologram.exist(args[1]))
                for (int x = 0; x < Hologram.get(args[1]).getLineList().size(); x++)
                    commandsList.add(String.valueOf(x));

            commandsList = Utils.dynamicTab(commandsList, args[2]);
        }

        return commandsList;
    }
}
