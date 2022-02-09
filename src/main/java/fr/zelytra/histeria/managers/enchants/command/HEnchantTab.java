package fr.zelytra.histeria.managers.enchants.command;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantData;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HEnchantTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {

            for (CustomEnchantData enchant : CustomEnchantData.values())
                commandsList.add(enchant.getKey().getKey());

            commandsList = Utils.dynamicTab(commandsList, args[0]);
        }
        return commandsList;
    }
}
