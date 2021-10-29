package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("home") || command.getName().equalsIgnoreCase("delhome")) {

            if (args.length != 1) return commandsList;

            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(sender.getName());

            for (Home home : customPlayer.getHomes())
                commandsList.add(home.getName());

            commandsList = Utils.dynamicTab(commandsList, args[0]);

        }
        return commandsList;
    }
}
