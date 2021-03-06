package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
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
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                Player player = e.getPlayer();

                CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
                Bukkit.getPluginManager().callEvent(customItemEvent);

                if(customItemEvent.isCancelled()){
                    return;
                }


                if (!Cooldown.cooldownCheck(player, customMaterial.getName())) {
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
    }
}
