package fr.zelytra.histeria.managers.jobs.listener;

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
        Component message = Component.text("§6[" + jobType.name() + "] §e" + player.getLang().get("jobs.xpGain") + " §6" + xp + "xp");

        player.getPlayer().sendActionBar(message);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.1f);

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

    /**
     * All Experience calculation constant
     */
    private static int xp_0_to_1 = 100;
    private static int xp_99_to_100 = 5000000;
    private static double a = Math.pow(xp_99_to_100 / xp_0_to_1, 1 / 99f);

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param xp Job total experience
     * @return the level
     */
    public static int getLevelFromXp(int xp) {
        return (int) Math.round(Math.log(1 - xp / xp_0_to_1 * (1 - a)) / Math.log(a));
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Job level
     * @return the amount of experience which a level can contain
     */
    public static int getXpFromLevel(int level) {
        return (int) (xp_0_to_1 * Math.pow(a, level - 1));
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Job level
     * @return the total amount of experience since input level
     */
    public static int getRecursiveXpFromLevel(int level) {
        if (level > 0)
            return (int) (xp_0_to_1 * (1 - Math.pow(a, level)) / (1 - a));

        return 0;
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Current level object
     * @param xp    Job total experience
     * @return the quantity of experience remaining before job level up
     */
    public static int getXpRemaining(int level, int xp) {
        if (level < 100)
            return getRecursiveXpFromLevel(level + 1) - xp;
        return 0;
    }

}
