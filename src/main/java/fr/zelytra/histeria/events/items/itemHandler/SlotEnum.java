/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.itemHandler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum SlotEnum {
    MAIN_HAND,
    OFF_HAND,
    HEAD,
    CHEST,
    LEGS,
    FEET;

    public ItemStack getItem(Player player) {
        switch (this) {
            case MAIN_HAND:
                return player.getInventory().getItemInMainHand();
            case OFF_HAND:
                return player.getInventory().getItemInOffHand();
            case HEAD:
                return player.getInventory().getHelmet();
            case CHEST:
                return player.getInventory().getChestplate();
            case LEGS:
                return player.getInventory().getLeggings();
            case FEET:
                return player.getInventory().getBoots();
            default:
                return null;
        }
    }

    public void removeItem(Player player) {
        switch (this) {
            case MAIN_HAND:
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                break;
            case OFF_HAND:
                player.getInventory().clear(45);
                break;
            case HEAD:
                player.getInventory().clear(39);
                break;
            case CHEST:
                player.getInventory().clear(38);
                break;
            case LEGS:
                player.getInventory().clear(37);
                break;
            case FEET:
                player.getInventory().clear(36);
                break;
        }
    }

    public static SlotEnum getArmorSlot(ItemStack item) {
        switch (item.getType().getEquipmentSlot()) {
            case HEAD:
                return HEAD;
            case CHEST:
                return CHEST;
            case LEGS:
                return LEGS;
            case FEET:
                return FEET;
            default:
                return null;
        }

    }

}
