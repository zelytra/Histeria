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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffler implements LuckyEvent {

    private final int luck;

    public Shuffler(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        List<ItemStack> inventory = new ArrayList<>();

        for (int x = 0; x <= 35; x++)
            inventory.add(e.getPlayer().getInventory().getContents()[x]);

        Collections.shuffle(inventory);

        for (int x = 0; x <= 35; x++)
            e.getPlayer().getInventory().setItem(x, inventory.get(x));

        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);

    }
}
