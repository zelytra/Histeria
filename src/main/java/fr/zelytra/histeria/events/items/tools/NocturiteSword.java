/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class NocturiteSword implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.NOCTURITE_SWORD;
    private final int itemCooldown = 15;

    @EventHandler
    public void swordAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), (Player) e.getDamager())) {
            Player player = (Player) e.getDamager();
            DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);
            durabilityHandler.iterate();

        }
    }

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if (e.isCancelled()) return;
        
        Player player = e.getPlayer();


        if (!Cooldown.cooldownCheck(player, customMaterial.getName(),true)) {
            return;
        }

        new Cooldown(player, itemCooldown, customMaterial.getName());
        ArrayList<Entity> NearEntity = (ArrayList<Entity>) player.getNearbyEntities(3.0, 3.0, 3.0);
        for (Entity entity : NearEntity) {

            Vector delta = new Vector(entity.getLocation().getX() - player.getLocation().getX(), 0, entity.getLocation().getZ() - player.getLocation().getZ());
            double norme = Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2) + Math.pow(delta.getZ(), 2));
            int coef = 2;
            Vector dir = new Vector((delta.getX() / norme) * coef, (delta.getY() / norme) + 1.5, (delta.getZ() / norme) * coef);

            entity.setVelocity(dir);

            if (entity instanceof Player) {
                ((Player) entity).playSound(entity.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1, 1);
            }
        }
        player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 190);
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1, 1);
    }
}

