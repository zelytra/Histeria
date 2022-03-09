/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.spawner;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityEggListener implements Listener {

    private final static double dropLuck = 0.005;

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEntity().getKiller().getName());
        if (player == null) return;

        if (player.getJob(JobType.FARMER).getLevel() != 100) return;
        Material egg = EntityEgg.getEgg(e.getEntityType());
        if (egg == null) return;

        // Check pheonix enchant
        ItemStack killerItem = e.getEntity().getKiller().getInventory().getItemInMainHand();
        if (killerItem != null && !CustomEnchantUtils.contain(killerItem, CustomEnchant.PHOENIX_BIRTH)) return;

        if (dropLuck <= Math.random()) return;
        e.getDrops().add(new ItemStack(egg));


    }
}
