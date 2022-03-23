/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class BlockSpreading implements LuckyEvent {

    private final int luck;

    public BlockSpreading(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        Material[] table = new Material[]{Material.ACACIA_PLANKS, Material.WARPED_PLANKS, Material.GRAVEL,
                Material.DIRT, Material.DIORITE, Material.DARK_OAK_LEAVES, Material.COBWEB, Material.PURPLE_WOOL,
                Material.BOOKSHELF, Material.SNOW_BLOCK, Material.CRACKED_STONE_BRICKS, Material.WHITE_TERRACOTTA,
                Material.CUT_RED_SANDSTONE};
        Location BLocation = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY() - 1, e.getBlock().getLocation().getZ());
        int range = 9;
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                final int fx = x;
                final int fz = z;
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Histeria.getInstance(), () -> {
                    BLocation.setX(e.getBlock().getLocation().getX() + fx);
                    BLocation.setZ(e.getBlock().getLocation().getZ() + fz);
                    int random = new Random().nextInt(1, 100);
                    int block = new Random().nextInt(0, table.length - 1);
                    if (random <= 40) {
                        BLocation.getBlock().setType(table[block]);
                    }
                }, (x + z + 50));
            }
        }
    }
}
