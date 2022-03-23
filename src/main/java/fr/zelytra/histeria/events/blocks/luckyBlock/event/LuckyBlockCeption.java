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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LuckyBlockCeption implements LuckyEvent {

    private final int luck;

    public LuckyBlockCeption(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.HONEYCOMB_BLOCK));
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.HONEYCOMB_BLOCK));
        e.getPlayer().sendMessage("§6Cut off one head, two more will grow back in its place...");
        e.getPlayer().playSound(e.getBlock().getLocation(), Sound.ENTITY_VILLAGER_HURT, 5, 1);


    }
}
