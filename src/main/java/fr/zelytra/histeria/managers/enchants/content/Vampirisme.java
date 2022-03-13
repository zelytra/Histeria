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

import java.util.Random;

public class Vampirisme implements Listener {

    private final static int PROC_LUCK = 1; // Luck to proc the effect in %
    private final static Random random = new Random();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.isCancelled()) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;

        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getInventory().getItemInMainHand();

        if (item != null && item.getType() != Material.AIR) {

            if (!CustomEnchantUtils.contain(item, CustomEnchant.VAMPIRISME)) return;

            int level = CustomEnchantUtils.getLevel(item, CustomEnchant.VAMPIRISME);

            if (PROC_LUCK * level >= random.nextInt(0, 100)) {
                ((LivingEntity) e.getEntity()).damage(2.0);

                damager.setHealth(damager.getHealth() + 2.0 >= damager.getMaxHealth() ? damager.getMaxHealth() : damager.getHealth() + 2.0);
                damager.getWorld().spawnParticle(Particle.GLOW, damager.getLocation(), 10, 0.1, 1, 0.1, 0.1);

            }
        }
    }
}
