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
        entityXpMap.put(EntityType.STRAY, 25);
        entityXpMap.put(EntityType.HUSK, 25);
        entityXpMap.put(EntityType.CAVE_SPIDER, 15);
        entityXpMap.put(EntityType.SLIME, 30);
        entityXpMap.put(EntityType.DROWNED, 25);
        entityXpMap.put(EntityType.WITCH, 35);

        entityXpMap.put(EntityType.WITHER_SKELETON, 15);
        entityXpMap.put(EntityType.WITHER, 50);

        entityXpMap.put(EntityType.PILLAGER, 15);
        entityXpMap.put(EntityType.VEX, 10);
        entityXpMap.put(EntityType.RAVAGER, 30);
        entityXpMap.put(EntityType.VINDICATOR, 20);
        entityXpMap.put(EntityType.ZOMBIE_VILLAGER, 45);
        entityXpMap.put(EntityType.EVOKER, 15);


        entityXpMap.put(EntityType.GUARDIAN, 30);
        entityXpMap.put(EntityType.ELDER_GUARDIAN, 35);
        entityXpMap.put(EntityType.ENDERMAN, 20);
        entityXpMap.put(EntityType.SHULKER, 50);
        entityXpMap.put(EntityType.PHANTOM, 15);

        entityXpMap.put(EntityType.HOGLIN, 15);
        entityXpMap.put(EntityType.ZOGLIN, 15);
        entityXpMap.put(EntityType.GHAST, 30);
        entityXpMap.put(EntityType.MAGMA_CUBE, 15);
        entityXpMap.put(EntityType.BLAZE, 15);
        entityXpMap.put(EntityType.PIGLIN_BRUTE, 20);
        entityXpMap.put(EntityType.PIGLIN, 15);
        entityXpMap.put(EntityType.ZOMBIFIED_PIGLIN, 20);

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
