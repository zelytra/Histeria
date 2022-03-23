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
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;

public class MLG implements LuckyEvent {

    private final int luck;

    public MLG(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {

        Location mlg = e.getPlayer().getLocation();
        mlg.setY(mlg.getY() + 300);
        e.getPlayer().teleport(mlg);
        e.getPlayer().sendMessage("§bMLG or death... glhf");

    }
}
