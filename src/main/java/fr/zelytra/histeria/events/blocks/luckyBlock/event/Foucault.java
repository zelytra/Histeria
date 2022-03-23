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
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Foucault implements LuckyEvent {

    private final int luck;

    public Foucault(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        ItemStack[] playerInv = e.getPlayer().getInventory().getContents();

        for (ItemStack itemStack : playerInv) {

            if (itemStack == null)
                continue;

            int randInv = new Random().nextInt(1, 100);

            if (randInv <= 50)
                itemStack.setAmount(0);
        }

        e.getPlayer().sendMessage("§9[§bJean-Pierre Foucault§9]§b You choose the 50/50 !");
    }
}
