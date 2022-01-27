package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import fr.zelytra.histeria.managers.player.CustomPlayer;

public class Miner extends Job implements JobInterface {

    public Miner(int level, int xp) {
        super(level, xp);
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

    @Override
    public VisualItemStack getItemMenu() {
        VisualItemStack visualItem = new VisualItemStack(CustomMaterial.BADGE_MINER, "§6Miner", level == 100,
                "§6Level §e" + level,
                "§6XP: §e" + formatBigNumber(xp),
                "",
                "§6XP remaining before level §6UP:",
                "§e" + formatBigNumber(ExperienceMath.getXpRemaining(level, xp)),
                "",
                "§6Total progression: §e" + getProgression()
        );
        return visualItem;
    }

    @Override
    public void executeReward(CustomPlayer player) {
        switch (level) {
            case 5:
                break;
            case 10:
                break;
            case 15:
                break;
            case 20:
                break;
            case 25:
                break;
            case 30:
                break;
            case 35:
                break;
            case 40:
                break;
            case 45:
                break;
            case 50:
                break;
            case 55:
                break;
            case 60:
                break;
            case 65:
                break;
            case 70:
                break;
            case 75:
                break;
            case 80:
                break;
            case 85:
                break;
            case 90:
                break;
            case 95:
                break;
            case 100:
                break;
        }
    }

    @Override
    public String getReward(int level) {
        return "null";
    }

}
