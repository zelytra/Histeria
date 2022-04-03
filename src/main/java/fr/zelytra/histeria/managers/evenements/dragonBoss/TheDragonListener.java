/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.dragonBoss;

import org.bukkit.Particle;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.List;

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
    public void onCrystal(EntityDamageByEntityEvent e) {

        // Player ejected when destroying ender cystal
        if (e.getEntityType() == EntityType.ENDER_CRYSTAL && e.getEntity().getWorld().getName().equalsIgnoreCase("world_the_end")) {
            if (!e.getEntity().getPersistentDataContainer().has(TheDragon.key)) return;

            List<Entity> nearEntities = e.getEntity().getNearbyEntities(10, 10, 10);
            for (Entity entity : nearEntities) {

                Vector delta = new Vector(entity.getLocation().getX() - e.getEntity().getLocation().getX(), 0, entity.getLocation().getZ() - e.getEntity().getLocation().getZ());
                double norme = Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2) + Math.pow(delta.getZ(), 2));
                double coef = 1.8;
                Vector dir = new Vector((delta.getX() / norme) * coef, (delta.getY() / norme) + 1.5, (delta.getZ() / norme) * coef);

                try {
                    entity.setVelocity(dir);
                } catch (Exception ignored) {

                }

            }
            e.getEntity().getWorld().spawnParticle(Particle.DRAGON_BREATH, e.getEntity().getLocation(), 190);
            e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.WITHER);
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
