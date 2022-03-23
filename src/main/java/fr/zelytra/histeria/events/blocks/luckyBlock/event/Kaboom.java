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
import org.bukkit.event.block.BlockBreakEvent;

public class Kaboom implements LuckyEvent {

    private final int luck;

    public Kaboom(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getPlayer().sendMessage("§cKABOOOOOOM");
        e.getPlayer().getWorld().createExplosion(e.getBlock().getLocation(), 20);

    }
}
