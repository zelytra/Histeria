/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.shop;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StandTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();
        if (args.length == 1) {

            for (Material material : Material.values())
                commandsList.add(material.name());
            for (CustomMaterial material : CustomMaterial.values())
                commandsList.add(material.getName());

            commandsList = Utils.dynamicTab(commandsList, args[0]);
        }
        return commandsList;
    }
}
