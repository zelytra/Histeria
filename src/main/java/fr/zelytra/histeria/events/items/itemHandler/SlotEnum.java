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

    public void removeItem(Player player){
        switch (this) {
            case MAIN_HAND:
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
            case OFF_HAND:
                player.getInventory().clear(45);
            case HEAD:
                player.getInventory().clear(5);
            case CHEST:
                player.getInventory().clear(6);
            case LEGS:
                player.getInventory().clear(7);
            case FEET:
                player.getInventory().clear(8);
        }
    }

}
