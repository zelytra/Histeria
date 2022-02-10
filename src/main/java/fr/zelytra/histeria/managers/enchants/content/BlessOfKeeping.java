/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.content;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BlessOfKeeping implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        for (ItemStack item : e.getPlayer().getInventory()) {
            if (item == null) continue;
            if (!CustomEnchantUtils.contain(item, CustomEnchant.BLESS_OF_KEEPING)) continue;
            e.getDrops().remove(item);
            e.getItemsToKeep().add(item);
        }
    }
}
