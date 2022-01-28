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

        entityXpMap.put(EntityType.ZOMBIE, 15);
        entityXpMap.put(EntityType.SPIDER, 15);
        entityXpMap.put(EntityType.SKELETON, 15);
        entityXpMap.put(EntityType.CREEPER, 15);

        entityXpMap.put(EntityType.ENDERMAN, 15);

        entityXpMap.put(EntityType.PLAYER, 500);


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
