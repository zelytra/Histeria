/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;

public class Nubert implements LuckyEvent {

    private final int luck;

    public Nubert(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        Entity entity = e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.AXOLOTL);
        entity.setCustomName("Nubert");
    }
}
