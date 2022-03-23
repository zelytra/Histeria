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
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HisteriteIngot implements LuckyEvent {

    private final int luck;

    public HisteriteIngot(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        ItemStack drop = new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 1).getItem();
        drop.setAmount(new Random().nextInt(1, 64));
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);

    }
}
