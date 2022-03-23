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

public class NocturiteCore implements LuckyEvent {

    private final int luck;

    public NocturiteCore(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                new CustomItemStack(CustomMaterial.NOCTURITE_CORE, 1).getItem());

    }
}
