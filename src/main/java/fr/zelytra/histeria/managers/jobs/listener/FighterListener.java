/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.listener;

import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Fighter;
import fr.zelytra.histeria.managers.jobs.utils.JobUtils;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class FighterListener implements Listener {

    private static final HashMap<EntityType, Integer> entityXpMap = new HashMap<>();

    {

        entityXpMap.put(EntityType.SKELETON, 15);
        entityXpMap.put(EntityType.CREEPER, 15);
        entityXpMap.put(EntityType.SPIDER, 15);
        entityXpMap.put(EntityType.ZOMBIE, 15);
        entityXpMap.put(EntityType.STRAY, 15);
        entityXpMap.put(EntityType.HUSK, 15);
        entityXpMap.put(EntityType.CAVE_SPIDER, 15);
        entityXpMap.put(EntityType.SLIME, 15);
        entityXpMap.put(EntityType.DROWNED, 15);
        entityXpMap.put(EntityType.WITCH, 15);

        entityXpMap.put(EntityType.WITHER_SKELETON, 15);
        entityXpMap.put(EntityType.WITHER, 15);

        entityXpMap.put(EntityType.PILLAGER, 15);
        entityXpMap.put(EntityType.VEX, 15);
        entityXpMap.put(EntityType.RAVAGER, 15);
        entityXpMap.put(EntityType.VINDICATOR, 15);
        entityXpMap.put(EntityType.ZOMBIE_VILLAGER, 15);
        entityXpMap.put(EntityType.EVOKER, 15);


        entityXpMap.put(EntityType.GUARDIAN, 15);
        entityXpMap.put(EntityType.ELDER_GUARDIAN, 15);
        entityXpMap.put(EntityType.ENDERMAN, 15);
        entityXpMap.put(EntityType.SHULKER, 15);
        entityXpMap.put(EntityType.PHANTOM, 15);

        entityXpMap.put(EntityType.HOGLIN, 15);
        entityXpMap.put(EntityType.ZOGLIN, 15);
        entityXpMap.put(EntityType.GHAST, 15);
        entityXpMap.put(EntityType.MAGMA_CUBE, 15);
        entityXpMap.put(EntityType.BLAZE, 15);
        entityXpMap.put(EntityType.PIGLIN_BRUTE, 15);
        entityXpMap.put(EntityType.PIGLIN, 15);
        entityXpMap.put(EntityType.ZOMBIFIED_PIGLIN, 15);

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {

        if (e.getEntity().getKiller() == null) return;
        if (!entityXpMap.containsKey(e.getEntity().getType())) return;

        // Job consuming experience
        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEntity().getKiller().getName());
        if (player == null) return;

        Fighter job = (Fighter) player.getJob(JobType.FIGHTER);
        double xp = entityXpMap.get(e.getEntity().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);


    }


}
