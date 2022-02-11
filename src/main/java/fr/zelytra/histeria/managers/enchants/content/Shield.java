package fr.zelytra.histeria.managers.enchants.content;

import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Shield implements Listener {

    public final static double PROC_LUCK = 0.5;
    private final static Random random = new Random();

    /**
     * Custom enchant : Shield <br>
     * Effect: Block entire damage from the hit when proc<br>
     * Luck: PROC_LUCK * enchantLvl
     */

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        double totalLuck = 0;

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (!CustomEnchantUtils.contain(item, CustomEnchant.SHIELD)) continue;
                totalLuck += PROC_LUCK * CustomEnchantUtils.getLevel(item, CustomEnchant.SHIELD);
            }
        }

        if (totalLuck <= 0) return;

        if (totalLuck >= random.nextInt(0, 100)) {
            for (Player connectedPlayer : Bukkit.getOnlinePlayers())
                connectedPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 2);
            player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, player.getLocation(), 100, 0.1, 0.8, 0.1, 0.1);
            e.setDamage(0.0);
        }


    }
}
