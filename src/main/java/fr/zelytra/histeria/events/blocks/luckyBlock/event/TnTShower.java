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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

public class TnTShower implements LuckyEvent {

    private final int luck;

    public TnTShower(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            for (int size = 4; size <= 20; size += 4) {
                for (int d = 0; d <= 20; d += 1) {

                    Location tnt = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());

                    tnt.setX(e.getBlock().getLocation().getX() + Math.cos(d) * size);
                    tnt.setZ(e.getBlock().getLocation().getZ() + Math.sin(d) * size);
                    tnt.setY(tnt.getY() + 10);

                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                        Entity dragonBall = e.getBlock().getWorld().spawnEntity(tnt, EntityType.PRIMED_TNT);
                        dragonBall.setVelocity(new Vector(0, -2, 0));
                    });

                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }
}

