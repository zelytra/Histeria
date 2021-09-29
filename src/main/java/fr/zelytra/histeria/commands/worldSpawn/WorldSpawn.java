/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.worldSpawn;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldSpawn implements CommandExecutor {
    public static WorldSpawnYML worldSpawn = new WorldSpawnYML();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        switch (command.getName()){

            case "setspawn":

                worldSpawn.save(((Player) sender).getLocation());
                LangMessage.sendMessage((Player) sender,"command.worldSpawnSet");
                return true;

            case "spawn":

                ((Player) sender).teleport(worldSpawn.getSpawn());
                return true;
        }

        return true;
    }
}
