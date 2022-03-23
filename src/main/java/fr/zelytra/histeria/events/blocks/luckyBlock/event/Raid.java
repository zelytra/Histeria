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
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class Raid implements LuckyEvent {

    private final int luck;

    public Raid(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        for (int x = 0; x <= 10; x++)
            e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.VINDICATOR);

        for (int x = 0; x <= 4; x++)
            e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.RAVAGER);

        for (int x = 0; x <= 5; x++)
            e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PILLAGER);

        for (int x = 0; x <= 2; x++)
            e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.EVOKER);

        e.getPlayer().sendMessage("§dGLHF boy !");
        for (Player player : Bukkit.getOnlinePlayers())
            player.playSound(e.getBlock().getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);


    }
}
