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
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemBreakBlockEvent;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

public class HisteriteShovel implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_SHOVEL;
    private final ArrayList<Material> dirt = new ArrayList<>(Arrays.asList(Material.DIRT, Material.SAND, Material.GRASS_BLOCK, Material.GRAVEL, Material.SOUL_SAND, Material.SOUL_SOIL, Material.RED_SAND, Material.FARMLAND, Material.PODZOL, Material.MYCELIUM, Material.COARSE_DIRT));
    private final int range = 2;

    @EventHandler
    public void breakBlock(CustomItemBreakBlockEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if (e.isCancelled()) return;

        Player player = e.getPlayer();

        DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);

        Location BLocation = e.getEvent().getBlock().getLocation();
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                BLocation.setX(e.getEvent().getBlock().getX() + x);
                BLocation.setZ(e.getEvent().getBlock().getZ() + z);
                if (isDirtBlock(BLocation.getBlock().getType())) {
                    BLocation.getBlock().breakNaturally();
                    durabilityHandler.iterate();
                    player.playSound(player.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 5, 1);
                }
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
}
