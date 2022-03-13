package fr.zelytra.histeria.managers.jobs.listener;

import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Enchanter;
import fr.zelytra.histeria.managers.jobs.utils.JobUtils;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.HashMap;

public class EnchanterListener implements Listener {

    private static final HashMap<Enchantment, Integer> xpMap = new HashMap<>();

    {
        xpMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 20);
        xpMap.put(Enchantment.PROTECTION_FIRE, 20);
        xpMap.put(Enchantment.PROTECTION_FALL, 25);
        xpMap.put(Enchantment.PROTECTION_EXPLOSIONS, 30);
        xpMap.put(Enchantment.PROTECTION_PROJECTILE, 30);
        xpMap.put(Enchantment.OXYGEN, 10);
        xpMap.put(Enchantment.WATER_WORKER, 10);
        xpMap.put(Enchantment.THORNS, 40);
        xpMap.put(Enchantment.DEPTH_STRIDER, 20);
        xpMap.put(Enchantment.FROST_WALKER, 15);
        xpMap.put(Enchantment.BINDING_CURSE, 25);
        xpMap.put(Enchantment.DAMAGE_ALL, 40);
        xpMap.put(Enchantment.DAMAGE_ARTHROPODS, 40);
        xpMap.put(Enchantment.DAMAGE_UNDEAD, 40);
        xpMap.put(Enchantment.KNOCKBACK, 20);
        xpMap.put(Enchantment.FIRE_ASPECT, 20);
        xpMap.put(Enchantment.LOOT_BONUS_MOBS, 30);
        xpMap.put(Enchantment.SWEEPING_EDGE, 35);
        xpMap.put(Enchantment.DIG_SPEED, 15);
        xpMap.put(Enchantment.SILK_TOUCH, 20);
        xpMap.put(Enchantment.DURABILITY, 25);
        xpMap.put(Enchantment.LOOT_BONUS_BLOCKS, 40);
        xpMap.put(Enchantment.ARROW_DAMAGE, 35);
        xpMap.put(Enchantment.ARROW_KNOCKBACK, 30);
        xpMap.put(Enchantment.ARROW_FIRE, 25);
        xpMap.put(Enchantment.ARROW_INFINITE, 40);
        xpMap.put(Enchantment.LUCK, 60);
        xpMap.put(Enchantment.LURE, 65);
        xpMap.put(Enchantment.LOYALTY, 50);
        xpMap.put(Enchantment.IMPALING, 55);
        xpMap.put(Enchantment.RIPTIDE, 45);
        xpMap.put(Enchantment.CHANNELING, 40);
        xpMap.put(Enchantment.MULTISHOT, 30);
        xpMap.put(Enchantment.QUICK_CHARGE, 25);
        xpMap.put(Enchantment.PIERCING, 30);
        xpMap.put(Enchantment.MENDING, 50);
        xpMap.put(Enchantment.VANISHING_CURSE, 20);
        xpMap.put(Enchantment.SOUL_SPEED, 15);
    }


    @EventHandler
    public void onEnchant(EnchantItemEvent e) {

        int xp = 0;

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEnchanter().getName());
        if (player == null) return;

        Enchanter job = (Enchanter) player.getJob(JobType.ENCHANTER);

        for (var entry : e.getEnchantsToAdd().entrySet()) {
            if (!xpMap.containsKey(entry.getKey())) continue;
            xp += xpMap.get(entry.getKey()) * entry.getValue();
        }

        if (xp != 0 && job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);

    }
}
