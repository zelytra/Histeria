/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.builder.commandsHandler;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCommands {
    private final String commandName;
    private final List<String> commands;

    public HelpCommands(String commandName) {
        this.commandName = commandName;
        this.commands = new ArrayList<>();
    }

    public void addCommand(String prefix, String... args) {
        this.commands.add("§6- /" + commandName + " " + prefix + " §b" + Arrays.toString(args));
    }

    public void printPlayer(Player player){
        player.sendMessage("§9§m               §r§l§9[  §r§l§bHisteria§9§l  ]§m               ");
        player.sendMessage("");
        for (String s :commands) {
            player.sendMessage(s);
        }
        player.sendMessage("");
        player.sendMessage("§9§m               §r§l§9[            ]§m               ");
    }
}
