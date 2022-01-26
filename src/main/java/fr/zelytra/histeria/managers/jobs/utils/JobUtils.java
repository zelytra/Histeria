/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.utils;

import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Enchanter;
import fr.zelytra.histeria.managers.jobs.content.Farmer;
import fr.zelytra.histeria.managers.jobs.content.Fighter;
import fr.zelytra.histeria.managers.jobs.content.Miner;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;

import java.util.List;

public abstract class JobUtils {

    public static void displayXP(JobType jobType, CustomPlayer player, double xp) {
        Component message = Component.text("§7[" + jobType.name() + "] §8" + player.getLang().get("jobs.xpGain") + " §7" + xp + "xp");

        player.getPlayer().sendActionBar(message);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, 0.1f);

    }

    /**
     * Method which check if all jobs are present in the list, and if not adding new one
     *
     * @param jobs Job list to check integrity
     * @return new jobs list after integrity checkin
     */
    public static List<JobInterface> completeJobList(List<JobInterface> jobs) {
        boolean jobExist = false;

        for (JobInterface job : jobs)
            if (job.getJob() == JobType.MINER)
                jobExist = true;
        if (!jobExist) jobs.add(new Miner(0, 0));

        jobExist = false;
        for (JobInterface job : jobs)
            if (job.getJob() == JobType.FARMER)
                jobExist = true;
        if (!jobExist) jobs.add(new Farmer(0, 0));

        jobExist = false;
        for (JobInterface job : jobs)
            if (job.getJob() == JobType.FIGHTER)
                jobExist = true;
        if (!jobExist) jobs.add(new Fighter(0, 0));

        jobExist = false;
        for (JobInterface job : jobs)
            if (job.getJob() == JobType.ENCHANTER)
                jobExist = true;
        if (!jobExist) jobs.add(new Enchanter(0, 0));

        return jobs;


    }

}
