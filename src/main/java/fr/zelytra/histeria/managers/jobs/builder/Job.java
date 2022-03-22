package fr.zelytra.histeria.managers.jobs.builder;

import fr.zelytra.histeria.managers.jobs.events.JobLevelUpEvent;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Job {

    public int level;
    public int xp;

    public Job(int level, int experience) {
        this.level = level;
        this.xp = experience;
    }

    public String getProgression() {
        int totalXP = ExperienceMath.getRecursiveXpFromLevel(100);
        double progression = (100.0 * xp) /  totalXP;

        String format = "0.000";
        NumberFormat formatter = new DecimalFormat(format);

        return formatter.format(progression) + "%";
    }

    /**
     * @param xp     given consume by the job
     * @param player The custom player to link the level up event
     * @return true when xp has been consume, and false when not consume or reached max level
     */
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

    public String formatBigNumber(int value) {
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(bd.longValue());
    }

    @Override
    public String toString() {
        return level + "lvl | " + xp + "xp | " + getProgression();
    }


}
