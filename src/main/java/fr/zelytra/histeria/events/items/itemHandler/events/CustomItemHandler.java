/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.itemHandler.events;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CustomItemHandler implements Listener {

    @EventHandler
    public void onCustomItemUse(PlayerInteractEvent e) {

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getPlayer().isOp() && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.COMPASS) {
                e.setCancelled(true);
            }

            if (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasTag(e.getPlayer().getInventory().getItemInOffHand())) {

                CustomItemUseEvent customItemUseEvent = new CustomItemUseEvent(e.getPlayer(), CustomItemStack.getCustomMaterial(e.getPlayer().getInventory().getItemInOffHand()), e.getPlayer().getInventory().getItemInOffHand(), e);
                Bukkit.getPluginManager().callEvent(customItemUseEvent);

                return;

            } else if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasTag(e.getPlayer().getInventory().getItemInMainHand()))) {

                CustomItemUseEvent customItemUseEvent = new CustomItemUseEvent(e.getPlayer(), CustomItemStack.getCustomMaterial(e.getPlayer().getInventory().getItemInMainHand()), e.getPlayer().getInventory().getItemInMainHand(), e);
                Bukkit.getPluginManager().callEvent(customItemUseEvent);

                return;

            }
        }
    }

    @EventHandler
    public void onCustomItemThrow(ProjectileLaunchEvent e) {

        if (e.getEntity().getShooter() instanceof Player) {
            Player shooter = (Player) e.getEntity().getShooter();

            if (CustomItemStack.hasTag(shooter.getInventory().getItemInOffHand())) {

                CustomItemLaunchEvent customItemLaunchEvent = new CustomItemLaunchEvent(shooter, CustomItemStack.getCustomMaterial(shooter.getInventory().getItemInOffHand()), shooter.getInventory().getItemInOffHand(), e);
                Bukkit.getPluginManager().callEvent(customItemLaunchEvent);

                return;

            } else if (CustomItemStack.hasTag(shooter.getInventory().getItemInMainHand())) {

                CustomItemLaunchEvent customItemLaunchEvent = new CustomItemLaunchEvent(shooter, CustomItemStack.getCustomMaterial(shooter.getInventory().getItemInMainHand()), shooter.getInventory().getItemInMainHand(), e);
                Bukkit.getPluginManager().callEvent(customItemLaunchEvent);

                return;

            }
        }
    }

    @EventHandler
    public void onCustomItemBreakBlock(BlockBreakEvent e) {

        if (CustomItemStack.hasTag(e.getPlayer().getInventory().getItemInMainHand())) {

            CustomItemBreakBlockEvent customItemBreakBlockEvent = new CustomItemBreakBlockEvent(e.getPlayer(), CustomItemStack.getCustomMaterial(e.getPlayer().getInventory().getItemInMainHand()), e.getPlayer().getInventory().getItemInMainHand(), e);
            Bukkit.getPluginManager().callEvent(customItemBreakBlockEvent);

            return;

        }

    }


}
