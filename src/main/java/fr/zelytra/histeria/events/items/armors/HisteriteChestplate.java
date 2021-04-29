package fr.zelytra.histeria.events.items.armors;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HisteriteChestplate implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_CHESTPLATE;

    @EventHandler
    public void armorDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        ItemStack armor = player.getInventory().getChestplate();
        if (armor == null || e.isCancelled() | !CustomItemStack.hasTag(armor)) {
            return;
        }
        DurabilityHandler durability = new DurabilityHandler(player, customMaterial, SlotEnum.getArmorSlot(armor));
        durability.iterate();
    }


}
