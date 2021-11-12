/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Slot implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 1) {

            setSlot(Integer.parseInt(args[0]));

            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "command.slotSet", String.valueOf(Bukkit.getServer().getMaxPlayers()));
            return true;
        } else if (args.length == 0) {
            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "command.slot", String.valueOf(Bukkit.getServer().getMaxPlayers()));
            return true;
        } else {
            LangMessage.sendMessage(player, "command.wrongSyntax");
            return false;
        }

    }

    public static void setSlot(int value) {
        try {
            Method serverGetHandle = Bukkit.getServer().getClass().getDeclaredMethod("getHandle");
            Object playerList = serverGetHandle.invoke(Bukkit.getServer());

            Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
            maxPlayersField.setAccessible(true);
            maxPlayersField.set(playerList, value);

        } catch (NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            return;
        }
    }
}
