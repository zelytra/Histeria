package fr.zelytra.histeria.commands.customItems;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HGiveTab implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> commandsList = new ArrayList<>();
        if (args.length == 1) {
            for (CustomMaterial material : CustomMaterial.values()) {
                commandsList.add(material.getName());
            }
            commandsList = Utils.dynamicTab(commandsList, args[0]);
        }
        return commandsList;
    }
}
