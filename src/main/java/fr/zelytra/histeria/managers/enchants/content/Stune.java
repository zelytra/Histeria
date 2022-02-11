/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.content;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Stune implements Listener {

    private final static int PROC_LUCK = 1; // Luck to proc the effect in %
    private final static Random random = new Random();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.isCancelled()) return;
        if (!(e.getDamager() instanceof Player) && (e.getEntity() instanceof LivingEntity)) return;

        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getInventory().getItemInMainHand();

        if (item != null && item.getType() != Material.AIR) {

            if (!CustomEnchantUtils.contain(item, CustomEnchant.VAMPIRISME)) return;

            int level = CustomEnchantUtils.getLevel(item, CustomEnchant.VAMPIRISME);

            if (PROC_LUCK * level >= random.nextInt(0, 100)) {
                ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,40,0));
                ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,40,0));
                e.getEntity().getWorld().spawnParticle(Particle.SOUL, e.getEntity().getLocation(), 50, 0.1, 1, 0.1, 0.1);

            }
        }
    }

}
