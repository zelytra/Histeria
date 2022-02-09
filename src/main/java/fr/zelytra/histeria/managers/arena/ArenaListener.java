/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.arena;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ArenaListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != Material.CHEST) return;
        if (e.isCancelled()) return;

        ArenaChest toRemove = null;
        for (ArenaChest chest : ArenaChest.chestList) {
            if (chest.getLocation().equals(e.getBlock().getLocation())) {
                toRemove = chest;
                break;
            }
        }

        if (toRemove != null) {
            toRemove.destroy();
            LangMessage.sendMessage(e.getPlayer(), "arena.breakChest");
        }

    }
}
