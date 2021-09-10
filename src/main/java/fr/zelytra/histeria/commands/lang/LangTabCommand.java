package fr.zelytra.histeria.commands.lang;

import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LangTabCommand implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> commandsList = new ArrayList<>();
        if (args.length == 1) {
            for (Lang lang : Lang.values()) {
                commandsList.add(lang.name());
            }
            commandsList = Utils.dynamicTab(commandsList, args[0]);
        }
        return commandsList;
    }

}
