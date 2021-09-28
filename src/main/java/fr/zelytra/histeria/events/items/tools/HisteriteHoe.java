/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class HisteriteHoe implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_HOE;
    private final ArrayList<Material> dirt = new ArrayList<>(Arrays.asList(Material.DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM, Material.COARSE_DIRT));
    private final int range = 2;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer()) && isDirtBlock(e.getClickedBlock().getType())) {

            Player player = e.getPlayer();
            CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
            Bukkit.getPluginManager().callEvent(customItemEvent);

            if(customItemEvent.isCancelled()){
                return;
            }

            Location BLocation = e.getClickedBlock().getLocation();
            DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);

            if (player.isSneaking()) {
                for (int x = -range; x <= range; x++) {
                    for (int z = -range; z <= range; z++) {
                        BLocation.setX(e.getClickedBlock().getX() + x);
                        BLocation.setZ(e.getClickedBlock().getZ() + z);
                        if (isDirtBlock(BLocation.getBlock().getType())) {
                            BLocation.getBlock().setType(Material.FARMLAND);
                            durabilityHandler.iterate();
                        }
                    }

                }
            } else {
                switch (getCardinalDirection(player)) {
                    case "North":
                        for (int z = 0; z <= 10; z++) {
                            BLocation.setZ(e.getClickedBlock().getZ() + z);
                            if (isDirtBlock(BLocation.getBlock().getType())) {
                                BLocation.getBlock().setType(Material.FARMLAND);
                            }
                        }
                        break;
                    case "South":
                        for (int z = 0; z <= 10; z++) {
                            BLocation.setZ(e.getClickedBlock().getZ() - z);
                            if (isDirtBlock(BLocation.getBlock().getType())) {
                                BLocation.getBlock().setType(Material.FARMLAND);
                            }
                        }
                        break;
                    case "East":
                        for (int x = 0; x <= 10; x++) {
                            BLocation.setX(e.getClickedBlock().getX() - x);
                            if (isDirtBlock(BLocation.getBlock().getType())) {
                                BLocation.getBlock().setType(Material.FARMLAND);
                            }
                        }
                        break;
                    case "West":
                        for (int x = 0; x <= 10; x++) {
                            BLocation.setX(e.getClickedBlock().getX() + x);
                            if (isDirtBlock(BLocation.getBlock().getType())) {
                                BLocation.getBlock().setType(Material.FARMLAND);
                            }
                        }

                        break;
                }
                durabilityHandler.iterate();
            }


        }
    }

    private boolean isDirtBlock(Material block) {
        for (Material material : dirt) {
            if (material == block) {
                return true;
            }
        }
        return false;
    }

    private String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw()) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    private String getDirection(double rot) {
        if (0 <= rot && rot < 67.5) {
            return "North";
        } else if (67.5 <= rot && rot < 157.5) {
            return "East";
        } else if (157.5 <= rot && rot < 247.5) {
            return "South";
        } else if (247.5 <= rot && rot < 337.5) {
            return "West";
        } else if (337.5 <= rot && rot < 360.0) {
            return "North";
        } else {
            return null;
        }
    }
}
