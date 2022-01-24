package fr.zelytra.histeria.managers.jobs.builder;

public class Job {

    private final int level;
    private final double xp;

    public Job() {
        this.level = 0;
        this.xp = 0;
    }

    public int getLevel() {
        return level;
    }

    public double getXp() {
        return xp;
    }

    public String getProgression(){
        //TODO calculation
        return "0%";
    }

    public boolean consumeXP(double xp){
        //TODO Handle level up and cp calculation
        return false;
    }
}
