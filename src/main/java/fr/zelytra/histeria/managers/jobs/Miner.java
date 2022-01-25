package fr.zelytra.histeria.managers.jobs;

import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobType;

public class Miner implements Job {

    private final int level;
    private final double xp;

    public Miner(int level,double xp) {
        this.level = level;
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "Miner : " + level + "lvl | " + xp + "xp | " + getProgression();
    }

    @Override
    public JobType getJob() {
        return JobType.MINER;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public double getXp() {
        return xp;
    }

    @Override
    public String getProgression() {
        return "0%";
    }

    @Override
    public boolean consumeXP(double xp) {
        return false;
    }
}
