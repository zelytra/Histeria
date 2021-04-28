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

}
