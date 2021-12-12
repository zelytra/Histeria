/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.repair;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilListener implements Listener {

    @EventHandler
    public void anvilListener(PrepareAnvilEvent e) {
        ItemStack[] content = e.getInventory().getContents();
        if (CustomItemStack.hasTag(content[0])) {
            CustomRepair itemRepair = CustomRepair.getByCustomMaterial(CustomItemStack.getCustomMaterial(content[0]));
            if (itemRepair != null && itemRepair.getMaterial() == CustomItemStack.getCustomMaterial(content[1])) {
                ItemStack result = content[0].clone();
                DurabilityHandler durabilityHandler = new DurabilityHandler(result, itemRepair.getItem());
                durabilityHandler.addDurability(content[1].getAmount() * itemRepair.getValue());
                e.setResult(result);
            }
        }
    }

    @EventHandler
    public void anvilClick(InventoryClickEvent e) {
        if (e.getView() == null || e.getView().getType() != InventoryType.ANVIL || e.getSlot() != 2) {
            return;
        }

        if (e.getCursor() != null && CustomItemStack.hasTag(e.getInventory().getItem(e.getSlot()))) {
            Player player = (Player) e.getWhoClicked();
            player.setItemOnCursor(e.getInventory().getItem(e.getSlot()));

            //Ingot amount handler
            ItemStack repairMaterial = e.getInventory().getItem(1);
            CustomRepair itemRepair = CustomRepair.getByCustomMaterial(CustomItemStack.getCustomMaterial(e.getInventory().getItem(0)));

            if (itemRepair != null && repairMaterial != null && repairMaterial.getAmount() > (itemRepair.getItem().getDurability() / itemRepair.getValue())) {
                repairMaterial.setAmount(repairMaterial.getAmount() - (itemRepair.getItem().getDurability() / itemRepair.getValue()));
            } else {
                e.getInventory().setItem(1, new ItemStack(Material.AIR));
            }

            e.getInventory().setItem(0, new ItemStack(Material.AIR));
            e.getInventory().setItem(2, new ItemStack(Material.AIR));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            }

        }

    }

}
