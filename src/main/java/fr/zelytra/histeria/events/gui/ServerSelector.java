package fr.zelytra.histeria.events.gui;

import fr.zelytra.histeria.builder.CustomGUI;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ServerSelector implements Listener {

    @EventHandler
    public void InterfaceInteract(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                SwitchServer switchServer = new SwitchServer((Player) e.getWhoClicked());
                switch (e.getCurrentItem().getType()) {
                    case PHANTOM_MEMBRANE:
                        switchServer.switchTo("beta");
                        break;
                    case NETHERITE_BLOCK:
                        switchServer.switchTo("nether");
                        break;
                    case GOLDEN_SWORD:
                        switchServer.switchTo("arena");
                        break;
                    case COMPASS:
                        switchServer.switchTo("faction");
                        break;
                    case NETHERITE_PICKAXE:
                        switchServer.switchTo("mining");
                        break;
                    case GRASS_BLOCK:
                        switchServer.switchTo("build");
                        break;
                    case DRAGON_EGG:
                        switchServer.switchTo("dragon");
                        break;
                }
            }
        }
    }
}
