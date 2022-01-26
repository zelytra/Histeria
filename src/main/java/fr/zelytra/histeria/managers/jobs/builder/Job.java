package fr.zelytra.histeria.managers.jobs.builder;

import fr.zelytra.histeria.managers.jobs.events.JobLevelUpEvent;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Bukkit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Job {

    public int level;
    public int xp;

    public Job(int level, int experience) {
        this.level = level;
        this.xp = experience;
    }

    public String getProgression() {
        int totalXP = ExperienceMath.getRecursiveXpFromLevel(100);
        double progression = (100.0 * xp) / totalXP;

        String format = "0.00";
        NumberFormat formatter = new DecimalFormat(format);

        return formatter.format(progression) + "%";
    }

    public boolean consumeXP(double xp, CustomPlayer player) {
        if (level >= 100) return false;
        this.xp += xp;
        int newLevel = ExperienceMath.getLevelFromXp(this.xp);

        if (level != newLevel) {
            level = newLevel;

            JobLevelUpEvent jobLevelUpEvent = new JobLevelUpEvent(player, (JobInterface) this);
            Bukkit.getPluginManager().callEvent(jobLevelUpEvent);

        }
        return true;
    }

    @Override
    public String toString() {
        return level + "lvl | " + xp + "xp | " + getProgression();
    }


}
