/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Elevator implements Listener {
    private List<Material> whitelist;

    {
        whitelist = new ArrayList<>(Arrays.asList(Material.AIR, Material.WHITE_CARPET, Material.ORANGE_CARPET, Material.YELLOW_CARPET, Material.LIME_CARPET, Material.PINK_CARPET, Material.GRAY_CARPET, Material.LIGHT_GRAY_CARPET, Material.CYAN_CARPET, Material.PURPLE_CARPET, Material.BLUE_CARPET, Material.BROWN_CARPET, Material.GREEN_CARPET, Material.RED_CARPET, Material.STONE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.BLACK_CARPET, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON, Material.WARPED_BUTTON, Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.CRIMSON_BUTTON, Material.POLISHED_BLACKSTONE_BUTTON));
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent e) {
        if (e.getTo().getY() > (e.getFrom().getY() + 0.4)) {
            Location blockLocation = e.getPlayer().getLocation();
            blockLocation.setY(blockLocation.getY() - 1);
            if (blockLocation.getBlock().getType() == CustomMaterial.ELEVATOR.getVanillaMaterial()) {
                for (int y = 1; y <= 20; y++) {
                    blockLocation.setY(blockLocation.getY() + 1);
                    if (blockLocation.getBlock().getType() == CustomMaterial.ELEVATOR.getVanillaMaterial()) {
                        blockLocation.setY(blockLocation.getY() + 1);
                        if (isWhiteList(blockLocation.getBlock().getType())) {
                            e.getPlayer().teleport(blockLocation);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20, 1);
                        } else {
                            LangMessage.sendMessage(e.getPlayer(), "elevator.obstructed");
                        }
                        return;
                    }

                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == CustomMaterial.ELEVATOR.getVanillaMaterial())
            e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(CustomMaterial.ELEVATOR.getVanillaMaterial()));
    }

    @EventHandler
    public void onSneaking(PlayerToggleSneakEvent e) {
        Location blockLocation = e.getPlayer().getLocation();
        blockLocation.setY(blockLocation.getY() - 1);
        if (blockLocation.getBlock().getType() == CustomMaterial.ELEVATOR.getVanillaMaterial()) {
            for (int y = 1; y <= 20; y++) {
                blockLocation.setY(blockLocation.getY() - 1);
                if (blockLocation.getBlock().getType() == CustomMaterial.ELEVATOR.getVanillaMaterial()) {
                    blockLocation.setY(blockLocation.getY() + 1);
                    if (isWhiteList(blockLocation.getBlock().getType())) {
                        e.getPlayer().teleport(blockLocation);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 20, 1);
                    } else {
                        LangMessage.sendMessage(e.getPlayer(), "elevator.obstructed");
                    }
                    return;
                }

            }
        }

    }

    private boolean isWhiteList(Material block) {
        for (Material material : whitelist) {
            if (material == block) {
                return true;
            }
        }
        return false;
    }
}
