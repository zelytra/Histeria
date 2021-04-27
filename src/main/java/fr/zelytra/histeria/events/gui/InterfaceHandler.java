package fr.zelytra.histeria.events.gui;

import fr.zelytra.histeria.builder.CustomGUI;
import fr.zelytra.histeria.builder.VisualType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InterfaceHandler implements Listener {

    @EventHandler
    public void InterfaceInteract(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI) {
            if (e.getCurrentItem() != null) {
                for (VisualType visualType : VisualType.values()) {
                    if (e.getCurrentItem().getType() == visualType.getItem().getType()) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
