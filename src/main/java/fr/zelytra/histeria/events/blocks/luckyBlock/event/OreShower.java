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
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreShower implements LuckyEvent {

    private final int luck;
    private final List<Material> materials = new ArrayList<>();

    {
        materials.add(Material.COAL_ORE);
        materials.add(Material.DEEPSLATE_COAL_ORE);
        materials.add(Material.IRON_ORE);
        materials.add(Material.DEEPSLATE_IRON_ORE);
        materials.add(Material.RAW_IRON_BLOCK);
        materials.add(Material.COPPER_ORE);
        materials.add(Material.DEEPSLATE_COPPER_ORE);
        materials.add(Material.RAW_COPPER_BLOCK);
        materials.add(Material.GOLD_ORE);
        materials.add(Material.DEEPSLATE_GOLD_ORE);
        materials.add(Material.RAW_GOLD_BLOCK);
        materials.add(Material.NETHER_GOLD_ORE);
        materials.add(Material.GILDED_BLACKSTONE);
        materials.add(Material.REDSTONE_ORE);
        materials.add(Material.DEEPSLATE_REDSTONE_ORE);
        materials.add(Material.EMERALD_ORE);
        materials.add(Material.DEEPSLATE_EMERALD_ORE);
        materials.add(Material.AMETHYST_CLUSTER);
        materials.add(Material.LAPIS_ORE);
        materials.add(Material.DEEPSLATE_LAPIS_ORE);
        materials.add(Material.DIAMOND_ORE);
        materials.add(Material.DEEPSLATE_DIAMOND_ORE);
        materials.add(CustomMaterial.HISTERITE_ORE.getVanillaMaterial());
        materials.add(Material.NETHER_QUARTZ_ORE);
        materials.add(Material.ANCIENT_DEBRIS);
    }

    public OreShower(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            int pileSize = new Random().nextInt(1, 80);
            Location pileHigh = e.getBlock().getLocation();

            for (int y = 0; y <= pileSize; y++) {
                Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                    e.getBlock().getWorld().getBlockAt(pileHigh).setType(materials.get(new Random().nextInt(0, materials.size() - 1)));
                });
                pileHigh.add(0, 1, 0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        });

    }
}
