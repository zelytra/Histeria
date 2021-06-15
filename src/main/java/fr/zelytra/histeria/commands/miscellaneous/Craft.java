package fr.zelytra.histeria.commands.miscellaneous;

import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class Craft implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            InterfaceBuilder interfaceBuilder = new InterfaceBuilder(InventoryType.CRAFTING, "Crafting");
            interfaceBuilder.open(player);
            return true;
        }
        return false;
    }
}
