package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;

public class Enchanter extends Job implements JobInterface {

    public Enchanter(int level, double xp) {
        super(level, xp);
    }

    @Override
    public String toString() {
        return "Enchanter : " + level + "lvl | " + xp + "xp | " + getProgression();
    }


    @Override
    public JobType getJob() {
        return JobType.ENCHANTER;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public double getXP() {
        return xp;
    }
}
