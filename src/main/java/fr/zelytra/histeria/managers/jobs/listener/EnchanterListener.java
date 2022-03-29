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
        xpMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 60);
        xpMap.put(Enchantment.PROTECTION_FIRE, 60);
        xpMap.put(Enchantment.PROTECTION_FALL, 75);
        xpMap.put(Enchantment.PROTECTION_EXPLOSIONS, 90);
        xpMap.put(Enchantment.PROTECTION_PROJECTILE, 90);
        xpMap.put(Enchantment.OXYGEN, 30);
        xpMap.put(Enchantment.WATER_WORKER, 30);
        xpMap.put(Enchantment.THORNS, 120);
        xpMap.put(Enchantment.DEPTH_STRIDER, 60);
        xpMap.put(Enchantment.FROST_WALKER, 45);
        xpMap.put(Enchantment.BINDING_CURSE, 75);
        xpMap.put(Enchantment.DAMAGE_ALL, 120);
        xpMap.put(Enchantment.DAMAGE_ARTHROPODS, 120);
        xpMap.put(Enchantment.DAMAGE_UNDEAD, 120);
        xpMap.put(Enchantment.KNOCKBACK, 60);
        xpMap.put(Enchantment.FIRE_ASPECT, 60);
        xpMap.put(Enchantment.LOOT_BONUS_MOBS, 90);
        xpMap.put(Enchantment.SWEEPING_EDGE, 105);
        xpMap.put(Enchantment.DIG_SPEED, 45);
        xpMap.put(Enchantment.SILK_TOUCH, 60);
        xpMap.put(Enchantment.DURABILITY, 75);
        xpMap.put(Enchantment.LOOT_BONUS_BLOCKS, 120);
        xpMap.put(Enchantment.ARROW_DAMAGE, 105);
        xpMap.put(Enchantment.ARROW_KNOCKBACK, 90);
        xpMap.put(Enchantment.ARROW_FIRE, 75);
        xpMap.put(Enchantment.ARROW_INFINITE, 120);
        xpMap.put(Enchantment.LUCK, 180);
        xpMap.put(Enchantment.LURE, 195);
        xpMap.put(Enchantment.LOYALTY, 150);
        xpMap.put(Enchantment.IMPALING, 165);
        xpMap.put(Enchantment.RIPTIDE, 135);
        xpMap.put(Enchantment.CHANNELING, 120);
        xpMap.put(Enchantment.MULTISHOT, 90);
        xpMap.put(Enchantment.QUICK_CHARGE, 75);
        xpMap.put(Enchantment.PIERCING, 90);
        xpMap.put(Enchantment.MENDING, 150);
        xpMap.put(Enchantment.VANISHING_CURSE, 60);
        xpMap.put(Enchantment.SOUL_SPEED, 45);
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
