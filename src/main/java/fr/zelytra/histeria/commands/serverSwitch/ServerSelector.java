/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.serverSwitch;

import fr.zelytra.histeria.builder.guiBuilder.*;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ServerSelector implements CommandExecutor, Interface, Listener {
    private String interfaceName = "§9Select a server:";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        InterfaceBuilder selector = new InterfaceBuilder(9, interfaceName);
        selector.setContent(contentBuilder());
        selector.open(player);
        return true;
    }


    @Override
    public ItemStack[] contentBuilder() {
        ItemStack[] content = new ItemStack[9];
        content[0] = new VisualItemStack(CustomMaterial.HISTERITE_CRYSTAL, "§7Beta", false).getItem();
        content[1] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[2] = new VisualItemStack(Material.NETHERITE_BLOCK, "§cNether", false).getItem();
        content[3] = new VisualItemStack(CustomMaterial.HISTERITE_SWORD, "§4Arena", false,"").getItem();
        content[4] = new VisualItemStack(CustomMaterial.UNCLAIM_FINDER, "§bFaction", false,"").getItem();
        content[5] = new VisualItemStack(CustomMaterial.HISTERITE_PICKAXE, "§6Mining", false,"").getItem();
        content[6] = new VisualItemStack(Material.GRASS_BLOCK, "§9Build", false).getItem();
        content[7] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[8] = new VisualItemStack(Material.DRAGON_EGG, "§5Dragon", false).getItem();
        return content;
    }

    @EventHandler
    public void InterfaceInteract(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().equals(interfaceName)) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                SwitchServer switchServer = new SwitchServer((Player) e.getWhoClicked());
                switch (e.getCurrentItem().getType()) {
                    case PHANTOM_MEMBRANE:
                        switchServer.switchTo("beta");
                        break;
                    case NETHERITE_BLOCK:
                        switchServer.switchTo("nether");
                        break;
                    case GOLDEN_SWORD:
                        switchServer.switchTo("arena");
                        break;
                    case COMPASS:
                        switchServer.switchTo("faction");
                        break;
                    case NETHERITE_PICKAXE:
                        switchServer.switchTo("mining");
                        break;
                    case GRASS_BLOCK:
                        switchServer.switchTo("build");
                        break;
                    case DRAGON_EGG:
                        switchServer.switchTo("dragon");
                        break;
                }
            }
        }
    }
}
