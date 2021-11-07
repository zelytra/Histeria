/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.SPEED_STICK;
    private final int itemCooldown = 15;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if(e.isCancelled()) return;

        Player player = e.getPlayer();

        //Cooldown check
        if (!Cooldown.cooldownCheck(player, customMaterial.getName(),true)) {
            return;
        }
        new Cooldown(player, itemCooldown, customMaterial.getName());

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2, false, false, true));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
        }

        ItemFunction.removeHeldItem(player, customMaterial);
    }


}
