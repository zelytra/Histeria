/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.inventoryLooker;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals)
            refreshInv(player, inventoryCanal);
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();

        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals)
            refreshInv(player, inventoryCanal);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals)
            refreshInv(player, inventoryCanal);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();

        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals)
            refreshInv(player, inventoryCanal);
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();

        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals)
            refreshInv(player, inventoryCanal);

    }

    private void refreshInv(Player player, InventoryCanal inventoryCanal) {

        if (player.getName().equalsIgnoreCase(inventoryCanal.getTarget().getName())) {

            Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.targetInvBuilder();
            }, 1);

        } else if (player.getName().equalsIgnoreCase(inventoryCanal.getViewer().getName())) {
            Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
                inventoryCanal.targetInvBuilder();
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
            }, 1);

        }
    }
}
