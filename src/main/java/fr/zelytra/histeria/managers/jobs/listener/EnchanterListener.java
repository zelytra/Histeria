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
        xpMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        xpMap.put(Enchantment.PROTECTION_FIRE, 1);
        xpMap.put(Enchantment.PROTECTION_FALL, 1);
        xpMap.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
        xpMap.put(Enchantment.PROTECTION_PROJECTILE, 1);
        xpMap.put(Enchantment.OXYGEN, 1);
        xpMap.put(Enchantment.WATER_WORKER, 1);
        xpMap.put(Enchantment.THORNS, 1);
        xpMap.put(Enchantment.DEPTH_STRIDER, 1);
        xpMap.put(Enchantment.FROST_WALKER, 1);
        xpMap.put(Enchantment.BINDING_CURSE, 1);
        xpMap.put(Enchantment.DAMAGE_ALL, 1);
        xpMap.put(Enchantment.DAMAGE_ARTHROPODS, 1);
        xpMap.put(Enchantment.DAMAGE_UNDEAD, 1);
        xpMap.put(Enchantment.KNOCKBACK, 1);
        xpMap.put(Enchantment.FIRE_ASPECT, 1);
        xpMap.put(Enchantment.LOOT_BONUS_MOBS, 1);
        xpMap.put(Enchantment.SWEEPING_EDGE, 1);
        xpMap.put(Enchantment.DIG_SPEED, 1);
        xpMap.put(Enchantment.SILK_TOUCH, 1);
        xpMap.put(Enchantment.DURABILITY, 1);
        xpMap.put(Enchantment.LOOT_BONUS_BLOCKS, 1);
        xpMap.put(Enchantment.ARROW_DAMAGE, 1);
        xpMap.put(Enchantment.ARROW_KNOCKBACK, 1);
        xpMap.put(Enchantment.ARROW_FIRE, 1);
        xpMap.put(Enchantment.ARROW_INFINITE, 1);
        xpMap.put(Enchantment.LUCK, 1);
        xpMap.put(Enchantment.LURE, 1);
        xpMap.put(Enchantment.LOYALTY, 1);
        xpMap.put(Enchantment.IMPALING, 1);
        xpMap.put(Enchantment.RIPTIDE, 1);
        xpMap.put(Enchantment.CHANNELING, 1);
        xpMap.put(Enchantment.MULTISHOT, 1);
        xpMap.put(Enchantment.QUICK_CHARGE, 1);
        xpMap.put(Enchantment.PIERCING, 1);
        xpMap.put(Enchantment.MENDING, 1);
        xpMap.put(Enchantment.VANISHING_CURSE, 1);
        xpMap.put(Enchantment.SOUL_SPEED, 1);
    }


    @EventHandler
    public void onEnchant(EnchantItemEvent e) {

        double xp = 0;

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
