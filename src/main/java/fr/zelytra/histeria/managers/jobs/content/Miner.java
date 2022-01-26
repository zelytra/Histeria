package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import org.bukkit.inventory.ItemStack;

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
                "§6XP: §e" + xp,
                "",
                "§6XP remaining before level §6UP:",
                "§e" + ExperienceMath.getXpRemaining(level, xp),
                "",
                "§6Total progression: §e" + getProgression()
        );
        return visualItem;
    }

    @Override
    public ItemStack[] getProgressionContent() {
        return new ItemStack[0];
    }

}
