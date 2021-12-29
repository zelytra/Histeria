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
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HealStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HEAL_STICK;
    private final int itemCooldown = 10;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if(e.isCancelled()) return;

        Player player = e.getPlayer();


        if (player.getHealth() == player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§6Your health is already full.");
            return;
        }
        //Cooldown check
        if (!Cooldown.cooldownCheck(player, customMaterial.getName(),true)) {
            return;
        }
        new Cooldown(player, itemCooldown, customMaterial.getName());

        double remainHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - player.getHealth();
        if (remainHealth < 8.0) {
            player.setHealth(player.getHealth() + remainHealth);
        } else {
            player.setHealth(player.getHealth() + 8.0);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
        }
        ItemFunction.removeHeldItem(player, customMaterial);
    }
}


