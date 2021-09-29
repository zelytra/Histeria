/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.broadcast;

import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BroadcastTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, @NotNull Command cmd, String msg, String[] arg) {
        List<String> commandsList = new ArrayList<>();


            if (arg.length == 1) {
                commandsList.add("all");
                commandsList.add("msg");
                commandsList.add("title");
                commandsList.add("hotbar");
                commandsList = Utils.dynamicTab(commandsList,arg[0]);

                return commandsList;
            }


        return commandsList;

    }

}
