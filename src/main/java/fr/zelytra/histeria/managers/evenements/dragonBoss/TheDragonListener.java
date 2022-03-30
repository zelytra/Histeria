/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.dragonBoss;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class TheDragonListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntityType() != TheDragon.type) return;

        TheDragon dragon = TheDragon.getSpecifiedBoss(e.getEntity());
        if (dragon == null) return;

        dragon.updateBossBar(dragon);

    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntityType() != TheDragon.type) return;

        TheDragon dragon = TheDragon.getSpecifiedBoss(e.getEntity());
        if (dragon == null) return;

        dragon.death();
    }
}
