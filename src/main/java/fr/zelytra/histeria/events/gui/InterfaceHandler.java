/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.gui;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InterfaceHandler implements Listener {

    @EventHandler
    public void InterfaceInteract(InventoryClickEvent e) {

        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;
        if (e.getCurrentItem() == null) return;

        for (VisualType visualType : VisualType.values()) {

            if (e.getCurrentItem().getType() == visualType.getItem().getType()) {

                if (!visualType.isCustom()) {
                    e.setCancelled(true);
                    return;
                } else if (CustomItemStack.hasTag(e.getCurrentItem())) {
                    e.setCancelled(true);
                    return;
                }


            }
        }
    }
}
