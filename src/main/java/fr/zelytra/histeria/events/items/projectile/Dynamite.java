/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.projectile;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemLaunchEvent;
import fr.zelytra.histeria.managers.hguard.HGuard;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class Dynamite implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.DYNAMITE;
    private final double range = 3.8;

    @EventHandler
    public void launch(CustomItemLaunchEvent e) {

        if (e.isCancelled()) return;

        if (e.getMaterial() == customMaterial)
            e.getEvent().getEntity().setCustomName(customMaterial.getName());

    }

    @EventHandler
    public void hit(ProjectileHitEvent e) {
        if (e.getEntity().getCustomName() == null || !(e.getEntity().getCustomName().equals(customMaterial.getName()))) {
            return;
        }

        if (HGuard.getByLocation(e.getEntity().getLocation()) != null) return;

        if (e.getHitEntity() != null) {
            explode(e.getHitEntity().getLocation());
            e.getHitEntity().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getHitEntity().getLocation(), 10);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.playSound(e.getHitEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
            }
        } else {
            explode(e.getHitBlock().getLocation());
            e.getHitBlock().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getHitBlock().getLocation(), 10);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.playSound(e.getHitBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
            }
        }


    }

    private void explode(Location BLocation) {
        Location origin = BLocation.clone();
        double radius = range * range;
        for (double x = -range; x <= range; x++) {
            for (double y = -range; y <= range; y++) {
                for (double z = -range; z <= range; z++) {
                    if ((x * x + y * y + z * z) < radius) {
                        BLocation.setX(origin.getX() + x);
                        BLocation.setY(origin.getY() + y);
                        BLocation.setZ(origin.getZ() + z);
                        if (canExplode(BLocation)) {

                            if (BLocation.getBlock().getType() == CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()) {
                                BLocation.getBlock().breakNaturally();
                                BLocation.getWorld().dropItem(BLocation,new ItemStack(CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()));
                                continue;
                            }

                            BLocation.getBlock().breakNaturally(new ItemStack(Material.AIR));
                        }
                    }
                }
            }
        }
    }

    private static boolean canExplode(Location BLocation) {
        if (BLocation.getBlock().getType() == Material.WATER || BLocation.getBlock().getType() == Material.LAVA
                || BLocation.getBlock().getType() == Material.LODESTONE
                || BLocation.getBlock().getType() == Material.BEDROCK
                || BLocation.getBlock().getType() == Material.END_PORTAL_FRAME) {
            return false;
        }
        if (HGuard.getByLocation(BLocation) != null) return false;
        int proba;
        switch (BLocation.getBlock().getType()) {
            case OBSIDIAN:
                proba = 10;
                if ((int) (Math.random() * (proba - 1) + 1) == 1) {
                    return true;
                }
                break;

            case ANVIL:
                proba = 12;
                if ((int) (Math.random() * (proba - 1) + 1) == 1) {
                    return true;
                }
                break;

            case ENDER_CHEST:
                proba = 15;
                if ((int) (Math.random() * (proba - 1) + 1) == 1) {
                    return true;
                }
                break;

            case ENCHANTING_TABLE:
                proba = 20;
                if ((int) (Math.random() * (proba - 1) + 1) == 1) {
                    return true;
                }
                break;

            default:
                return true;
        }
        return false;
    }

}
