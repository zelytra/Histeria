/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.visual;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CustomAttack {

    private static final Random random = new Random();

    public static void dragonBall(Entity entity, EntityType fallingType) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            for (int size = 4; size <= 20; size += 4) {
                for (int d = 0; d <= 20; d += 1) {
                    Location ballLoc = new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
                    ballLoc.setX(entity.getLocation().getX() + Math.cos(d) * size);
                    ballLoc.setZ(entity.getLocation().getZ() + Math.sin(d) * size);
                    ballLoc.setY(ballLoc.getY() + 10);
                    Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                        Entity fallingEntity = entity.getWorld().spawnEntity(ballLoc, fallingType);
                        fallingEntity.setVelocity(new Vector(0, -2, 0));
                    });
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Summon lingering potions and make it rain on the ground
     *
     * @param entity who execute the attack
     */
    public static void potionBombingRaid(Entity entity) {
        List<PotionEffectType> potionEffectTypeList = new ArrayList<>();

        potionEffectTypeList.add(PotionEffectType.SLOW_FALLING);
        potionEffectTypeList.add(PotionEffectType.SLOW);
        potionEffectTypeList.add(PotionEffectType.SLOW_DIGGING);
        potionEffectTypeList.add(PotionEffectType.POISON);
        potionEffectTypeList.add(PotionEffectType.BLINDNESS);
        potionEffectTypeList.add(PotionEffectType.CONFUSION);
        potionEffectTypeList.add(PotionEffectType.HUNGER);
        potionEffectTypeList.add(PotionEffectType.LEVITATION);


        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            for (int x = 0; x <= random.nextInt(5, 30); x++) {

                ItemStack item = new ItemStack(Material.LINGERING_POTION);
                PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

                potionMeta.addCustomEffect(new PotionEffect(potionEffectTypeList.get(random.nextInt(0, potionEffectTypeList.size() - 1)),
                        random.nextInt(5, 20) * 20, random.nextInt(0, 4)), true);
                potionMeta.setColor(Color.PURPLE);

                item.setItemMeta(potionMeta);
                Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                    ThrownPotion potion = (ThrownPotion) entity.getWorld().spawnEntity(entity.getLocation().add(0, 2, 0), EntityType.SPLASH_POTION);
                    potion.setItem(item);
                    potion.setVelocity(Vector.getRandom().subtract(new Vector(0.5, 0, 0.5)));
                });


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * Levitate all player in the air and push them back to ground very hard
     *
     * @param entity who execute the attack
     * @param intensity of the attack
     * @param radius of the attack (in blocks)
     */
    public static void vortex(Entity entity, AttackIntensity intensity, int radius) {
        List<Entity> nearbyEntities = entity.getNearbyEntities(radius, radius, radius);

        for (Entity e : nearbyEntities) {

            if (!(e instanceof LivingEntity)) continue;
            Vector toVortex = e.getLocation().toVector().subtract(e.getLocation().toVector()).normalize();

            switch (intensity) {
                case LIGHT -> {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1, true, true));
                    e.setVelocity(toVortex.multiply(-0.7));
                    break;
                }

                case MEDIUM -> {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 80, 2, true, true));
                    e.setVelocity(toVortex.multiply(-1));
                    break;
                }

                case HARD -> {
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3, true, true));
                    e.setVelocity(toVortex.multiply(-2));
                    break;
                }

            }

        }
    }

}

enum AttackIntensity {
    LIGHT,
    MEDIUM,
    HARD;
}
