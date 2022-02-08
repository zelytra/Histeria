/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.listener;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantingTableCustomEnchant implements Listener {

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEnchanter().getName());
        if (player == null) return;

        if (player.getJob(JobType.ENCHANTER).getLevel() != 100) return;

        //TODO Handle custom enchant draw

        e.getItem().addEnchantment(CustomEnchant.BLESS_OF_KEEPING,1);
        Utils.updateCustomEnchant(e.getItem());

    }
}
