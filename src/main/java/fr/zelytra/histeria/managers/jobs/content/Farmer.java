package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;

public class Farmer extends Job implements JobInterface {

    public Farmer(int level, double xp) {
        super(level, xp);
    }

    @Override
    public String toString() {
        return "Farmer : " + level + "lvl | " + xp + "xp | " + getProgression();
    }


    @Override
    public JobType getJob() {
        return JobType.FARMER;
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
