package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class VillagerTrade implements Listener {

    @EventHandler
    public void onTrade(InventoryClickEvent e) {

        if (e.getInventory().getType() == InventoryType.MERCHANT)
            if (e.getSlotType() == InventoryType.SlotType.RESULT)
                if (e.getCurrentItem() != null && Utils.isForbiddenArmorPiece(e.getCurrentItem()))
                    e.setCancelled(true);

    }
}
