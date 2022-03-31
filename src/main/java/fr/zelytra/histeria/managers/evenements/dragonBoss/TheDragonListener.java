/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.dragonBoss;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class TheDragonListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getEntityType() != TheDragon.type) return;

        TheDragon dragon = TheDragon.getSpecifiedBoss(e.getEntity());
        if (dragon == null) return;

        dragon.updateBossBar(dragon);
        if (e instanceof EntityDamageByEntityEvent) {

            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;

            if (damageEvent.getDamager() instanceof Player) {
                dragon.damage((Player) damageEvent.getDamager(), damageEvent.getDamage());
            } else if (damageEvent.getDamager() instanceof AbstractArrow) {
                AbstractArrow abstractArrow = (AbstractArrow) damageEvent.getDamager();
                dragon.damage((Player) abstractArrow.getShooter(), damageEvent.getDamage());
            }
        }

    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntityType() != TheDragon.type) return;

        TheDragon dragon = TheDragon.getSpecifiedBoss(e.getEntity());
        if (dragon == null) return;

        dragon.death(e.getEntity().getLocation());
    }


}
