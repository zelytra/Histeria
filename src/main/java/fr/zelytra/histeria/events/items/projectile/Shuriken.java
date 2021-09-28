/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.projectile;

import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class Shuriken implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.SHURIKEN;

    @EventHandler
    public void launch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            if (CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), (Player) e.getEntity().getShooter()) || CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), (Player) e.getEntity().getShooter())) {
                CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial, (Player) e.getEntity().getShooter());
                Bukkit.getPluginManager().callEvent(customItemEvent);

                if(customItemEvent.isCancelled()){
                    e.setCancelled(true);
                }
                e.getEntity().setCustomName(customMaterial.getName());
            }
        }
    }

    @EventHandler
    public void hit(ProjectileHitEvent e) {
        if (e.getEntity().getCustomName() == null || e.getHitEntity() == null) {
            return;
        }
        if (e.getEntity().getCustomName().equals(customMaterial.getName())) {
            if (e.getHitEntity() instanceof LivingEntity) {
                ((LivingEntity) e.getHitEntity()).damage(4.0);

            }
        }

    }
}
