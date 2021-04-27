package fr.zelytra.histeria.commands;

import fr.zelytra.histeria.builder.Interface;
import fr.zelytra.histeria.builder.InterfaceBuilder;
import fr.zelytra.histeria.builder.VisualItemStack;
import fr.zelytra.histeria.builder.VisualType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ServerSelector implements CommandExecutor, Interface{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        InterfaceBuilder selector = new InterfaceBuilder(9, "§9Select a server:");
        selector.setContent(contentBuilder());
        selector.open(player);
        return true;
    }


    @Override
    public ItemStack[] contentBuilder() {
        ItemStack[] content = new ItemStack[9];
        content[0] = new VisualItemStack(Material.PHANTOM_MEMBRANE, "§7Beta", false).getItem();
        content[1] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[2] = new VisualItemStack(Material.NETHERITE_BLOCK, "§cNether", false).getItem();
        content[3] = new VisualItemStack(Material.GOLDEN_SWORD, "§4Arena", false).getItem();
        content[4] = new VisualItemStack(Material.COMPASS, "§bFaction", false).getItem();
        content[5] = new VisualItemStack(Material.NETHERITE_PICKAXE, "§6Mining", false).getItem();
        content[6] = new VisualItemStack(Material.GRASS_BLOCK, "§9Build", false).getItem();
        content[7] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[8] = new VisualItemStack(Material.DRAGON_EGG, "§5Dragon", false).getItem();
        return content;
    }
}
