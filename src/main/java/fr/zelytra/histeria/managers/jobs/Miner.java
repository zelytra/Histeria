package fr.zelytra.histeria.managers.jobs;

import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.visual.chat.Emote;

public class Miner extends Job {

    private Emote badge = Emote.FIGHT;

    public Miner() {
        super();
    }

    @Override
    public String toString() {
        return "Miner : " + getLevel() + "lvl | " + getXp() + "xp | " + getProgression();
    }

}
