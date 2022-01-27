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
import fr.zelytra.histeria.managers.jobs.content.Farmer;
import fr.zelytra.histeria.managers.jobs.utils.JobUtils;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class FarmerListener implements Listener {

    private static final HashMap<Material, Integer> seedXpMap = new HashMap<>();

    {
        seedXpMap.put(Material.WHEAT, 1);
        seedXpMap.put(Material.POTATOES, 1);
        seedXpMap.put(Material.BEETROOTS, 1);
        seedXpMap.put(Material.CARROTS, 1);

    }

    @EventHandler
    public void onSeedBreak(BlockBreakEvent e) {

        if (!seedXpMap.containsKey(e.getBlock().getType())) return;

        // Checking age of the seed
        BlockData data = e.getBlock().getBlockData();
        if (!(data instanceof Ageable)) return;
        Ageable blockData = (Ageable) data;
        if (blockData.getAge() != blockData.getMaximumAge()) return;

        // Job consuming experience
        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        if (player == null) return;

        Farmer job = (Farmer) player.getJob(JobType.FARMER);
        double xp = seedXpMap.get(e.getBlock().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);


    }
}
