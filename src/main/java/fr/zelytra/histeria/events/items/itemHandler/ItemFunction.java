package fr.zelytra.histeria.events.items.itemHandler;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFunction {

    public static void removeHeldItem(Player player, CustomMaterial material) {
        if(player.getGameMode()== GameMode.CREATIVE){
            return;
        }
        if (CustomItemStack.hasCustomItemInMainHand(material.getName(), player)) {
            if (player.getInventory().getItemInMainHand().getAmount() > 1) {
                ItemStack newItem = player.getInventory().getItemInMainHand();
                newItem.setAmount(newItem.getAmount() - 1);
                player.getInventory().setItemInMainHand(newItem);
            } else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.VOID_AIR));
            }
        } else if (CustomItemStack.hasCustomItemInOffHand(material.getName(), player)) {
            if (player.getInventory().getItemInOffHand().getAmount() > 1) {
                ItemStack newItem = player.getInventory().getItemInOffHand();
                newItem.setAmount(newItem.getAmount() - 1);
                player.getInventory().setItemInOffHand(newItem);
            } else {
                player.getInventory().setItemInOffHand(new ItemStack(Material.VOID_AIR));
            }
        }
    }

}
