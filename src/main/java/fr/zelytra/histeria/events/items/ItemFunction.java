package fr.zelytra.histeria.events.items;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFunction {

    public static void removeHeldItem(PlayerInteractEvent e, CustomMaterial material) {
        switch (e.getHand()) {
            case HAND:
                if (CustomItemStack.hasCustomItemInMainHand(material.getName(), e.getPlayer())) {
                    if (e.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        ItemStack newItem = e.getPlayer().getInventory().getItemInMainHand();
                        newItem.setAmount(newItem.getAmount() - 1);
                        e.getPlayer().getInventory().setItemInMainHand(newItem);
                    } else {
                        e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    }
                }
                break;
            case OFF_HAND:
                if (CustomItemStack.hasCustomItemInOffHand(material.getName(), e.getPlayer())) {
                    if (e.getPlayer().getInventory().getItemInOffHand().getAmount() > 1) {
                        ItemStack newItem = e.getPlayer().getInventory().getItemInOffHand();
                        newItem.setAmount(newItem.getAmount() - 1);
                        e.getPlayer().getInventory().setItemInOffHand(newItem);
                    } else {
                        e.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                    }
                }
                break;
            default:
                break;
        }
    }
}
