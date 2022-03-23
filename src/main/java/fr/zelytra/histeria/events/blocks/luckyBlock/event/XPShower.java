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
import org.bukkit.Sound;
import org.bukkit.event.block.BlockBreakEvent;

public class XPShower implements LuckyEvent {

    private final int luck;

    public XPShower(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getPlayer().playSound(e.getBlock().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        e.getPlayer().setLevel(e.getPlayer().getLevel() + (80 / (e.getPlayer().getLevel() + 1)));
        e.getPlayer().sendMessage("§aYou feel lucky today !");

    }
}
