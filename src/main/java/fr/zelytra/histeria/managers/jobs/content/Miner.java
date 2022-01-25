package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;

public class Miner extends Job implements JobInterface  {

    public Miner(int level,int xp) {
        super(level,xp);
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
    public double getXP() {
        return xp;
    }

}
