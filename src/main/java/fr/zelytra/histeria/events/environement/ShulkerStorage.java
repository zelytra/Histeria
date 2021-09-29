/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.environement;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;

public class ShulkerStorage implements Listener {
    private ArrayList<Material> blacklist = new ArrayList<Material>();
    {
        blacklist.add(Material.SHULKER_BOX);
        blacklist.add(Material.WHITE_SHULKER_BOX);
        blacklist.add(Material.LIGHT_GRAY_SHULKER_BOX);
        blacklist.add(Material.GRAY_SHULKER_BOX);
        blacklist.add(Material.BLACK_SHULKER_BOX);
        blacklist.add(Material.BROWN_SHULKER_BOX);
        blacklist.add(Material.RED_SHULKER_BOX);
        blacklist.add(Material.ORANGE_SHULKER_BOX);
        blacklist.add(Material.YELLOW_SHULKER_BOX);
        blacklist.add(Material.LIME_SHULKER_BOX);
        blacklist.add(Material.GREEN_SHULKER_BOX);
        blacklist.add(Material.CYAN_SHULKER_BOX);
        blacklist.add(Material.LIGHT_BLUE_SHULKER_BOX);
        blacklist.add(Material.BLUE_SHULKER_BOX);
        blacklist.add(Material.PURPLE_SHULKER_BOX);
        blacklist.add(Material.MAGENTA_SHULKER_BOX);
        blacklist.add(Material.PINK_SHULKER_BOX);
    }

    @EventHandler
    public void shulkerMoveEvent(InventoryClickEvent e) {

        if (e.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            if (e.getCurrentItem() != null && blacklist.contains(e.getCurrentItem().getType())) {
                e.setCancelled(true);
            }
        }
        if (e.getClick().isKeyboardClick()) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            return;
        }

    }
}
