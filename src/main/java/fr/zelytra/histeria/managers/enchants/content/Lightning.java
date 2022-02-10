package fr.zelytra.histeria.managers.enchants.content;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Lightning implements Listener {

    private final static int PROC_LUCK = 1; // Luck to proc the effect in %
    private final static Random random = new Random();

    /**
     * Custom enchant : Lightning <br>
     * Effect: Summon lightning on entity, make 2 hearts damage and put fire<br>
     * Luck: lvl 1: 1% | lvl 2: 2%
     */

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.isCancelled()) return;
        if (!(e.getDamager() instanceof Player) && (e.getEntity() instanceof LivingEntity)) return;

        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getInventory().getItemInMainHand();

        if (item != null && item.getType() != Material.AIR) {

            if (!CustomEnchantUtils.contain(item, CustomEnchant.LIGHTNING)) return;

            int level = CustomEnchantUtils.getLevel(item, CustomEnchant.LIGHTNING);
           
            if (PROC_LUCK * level >= random.nextInt(0, 100)) {                
                if (e.getEntity().getLocation().getY() >= e.getEntity().getWorld().getHighestBlockAt(e.getEntity().getLocation()).getY())
                    lightning(e.getEntity());
            }


        }

    }

    private void lightning(Entity entity) {
        LivingEntity livingEntity =(LivingEntity) entity;
        
        livingEntity.setFireTicks(20 * 2);
        livingEntity.damage(2.0);        
        entity.getWorld().strikeLightningEffect(entity.getLocation());

    }
}
