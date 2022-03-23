/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.builder;

import org.bukkit.event.block.BlockBreakEvent;

public interface LuckyEvent {

    double getLuck();
    void run(BlockBreakEvent e);

}
