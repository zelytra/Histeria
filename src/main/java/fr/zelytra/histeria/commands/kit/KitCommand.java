/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.kit;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.kit.Kit;
import fr.zelytra.histeria.managers.kit.KitEnum;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KitCommand implements CommandExecutor, Listener {

    private final String guiName = "§9Kit";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        openMenu(player);

        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI) {
            if (e.getView().getTitle().equals(guiName)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case IRON_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.DEFAULT);
                            break;
                        case GOLD_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.VOTE);
                            break;
                        case DIAMOND_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.LORD);
                            break;
                        case PURPUR_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.MONARCH);
                            break;
                        case PURPUR_PILLAR:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.DEMIGOD);
                            break;
                        case BARRIER:
                            openMenu((Player) e.getWhoClicked());
                            break;
                    }
                }
            }
        }
    }

    private void openMenu(Player player) {
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(9, guiName);
        interfaceBuilder.setContent(getMenu(CustomPlayer.getCustomPlayer(player.getName())));
        interfaceBuilder.open(player);
    }

    private void openKitDisplay(Player player, KitEnum kitEnum) {
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(54, guiName);
        interfaceBuilder.setContent(getKitDisplay(new Kit(kitEnum)));
        interfaceBuilder.open(player);
    }

    private ItemStack[] getMenu(CustomPlayer player) {
        ItemStack[] content = new ItemStack[9];
        content[0] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[1] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[7] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[8] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[2] = new VisualItemStack(Material.IRON_BLOCK, "§7Histerien", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[3] = new VisualItemStack(Material.GOLD_BLOCK, "§aVote", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[4] = new VisualItemStack(Material.DIAMOND_BLOCK, "§bLord", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[5] = new VisualItemStack(Material.PURPUR_BLOCK, "§cMonarch", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[6] = new VisualItemStack(Material.PURPUR_PILLAR, "§5Demigod", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();

        return content;
    }

    private ItemStack[] getKitDisplay(Kit kit) {

        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x <= 9; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        for (int x = 20; x <= 26; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        for (int x = 45; x <= 52; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[11] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[18] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[27] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[29] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[35] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[36] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[38] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[44] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[53] = new VisualItemStack(Material.BARRIER, "§cBack", false).getItem();

        List<ItemStack> items = kit.getItemList();

        if (items.size() >= 4) {
            content[10] = items.get(0);
            content[19] = items.get(1);
            content[28] = items.get(2);
            content[37] = items.get(3);
        }

        int count = 4;
        for (int x = 12; x <= 16; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        count = 9;
        for (int x = 30; x <= 34; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        count = 14;
        for (int x = 39; x <= 43; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        return content;

    }

    private void giveKit(Player player) {

    }
}
