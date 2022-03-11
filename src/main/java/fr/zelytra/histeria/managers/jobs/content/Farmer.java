package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.spawner.EntityEgg;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Farmer extends Job implements JobInterface {

    public Farmer(int level, int xp) {
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

    @Override
    public VisualItemStack getItemMenu() {
        VisualItemStack visualItem = new VisualItemStack(CustomMaterial.BADGE_FARMER, "§6Farmer", level == 100,
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
                ItemStack item = Utils.EnchantedItemStack(Material.IRON_HOE, Enchantment.DURABILITY, 1);
                Utils.safeGive(player.getPlayer(), item);
                item = new ItemStack(Material.WATER_BUCKET, 1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(2500);
                break;
            case 10:
                item = new ItemStack(Material.BEETROOT_SEEDS, 16);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(3500);
                break;
            case 15:
                item = Utils.EnchantedItemStack(Material.IRON_HOE, Enchantment.DURABILITY, 3);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 20:
                Utils.safeGive(player.getPlayer(), new ItemStack(Material.WHEAT, 32));
                player.getBankAccount().addMoney(4500);
                break;
            case 25:
                player.getBankAccount().addMoney(5500);
                break;
            case 30:
                Utils.safeGive(player.getPlayer(), EntityEgg.SPIDER.getSpawner());
                player.getBankAccount().addMoney(7500);
                break;
            case 35:
                item = Utils.EnchantedItemStack(Material.DIAMOND_HOE, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(10000);
                break;
            case 40:
                player.getBankAccount().addMoney(12500);
                break;
            case 45:
                Utils.safeGive(player.getPlayer(), EntityEgg.ZOMBIE.getSpawner());
                break;
            case 50:

                break;
            case 55:
                player.getBankAccount().addMoney(100000);
                break;
            case 60:
                item = new CustomItemStack(CustomMaterial.HISTERITE_HOE, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 65:
                Utils.safeGive(player.getPlayer(), EntityEgg.SKELETON.getSpawner());
                break;
            case 70:
                player.getBankAccount().addMoney(200000);
                break;
            case 75:
                Utils.safeGive(player.getPlayer(), EntityEgg.PIGLIN_BRUTE.getSpawner());
                break;
            case 80:
                item = new CustomItemStack(CustomMaterial.HISTERITE_HOE, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 3);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 85:
                Utils.safeGive(player.getPlayer(), EntityEgg.SLIME.getSpawner());
                break;
            case 90:
                Utils.safeGive(player.getPlayer(), EntityEgg.ENDERMAN.getSpawner());
                player.getBankAccount().addMoney(1);
                break;
            case 95:
                player.getBankAccount().addMoney(1000000);
                break;
            case 100:
                break;
        }

    }

    @Override
    public List<String> getReward(int level) {
        List<String> lore = new ArrayList<>();
        switch (level) {
            case 5:
                lore.add("x1 Iron hoe [Unbreaking I]");
                lore.add(formatBigNumber(2500) + "§f" + Emote.GOLD);
                break;
            case 10:
                lore.add("x16 Beetroots");
                lore.add(formatBigNumber(3500) + "§f" + Emote.GOLD);
                break;
            case 15:
                lore.add("x1 Iron hoe [Unbreaking III]");
                break;
            case 20:
                lore.add("x32 Wheat");
                lore.add(formatBigNumber(4500) + "§f" + Emote.GOLD);
                break;
            case 25:
                lore.add(formatBigNumber(5500) + "§f" + Emote.GOLD);
                break;
            case 30:
                lore.add("x1 Spider's Spawner");
                lore.add(formatBigNumber(7500) + "§f" + Emote.GOLD);
                break;
            case 35:
                lore.add("x1 Diamond hoe [Unbreaking II]");
                lore.add(formatBigNumber(10000) + "§f" + Emote.GOLD);
                break;
            case 40:
                lore.add(formatBigNumber(12500) + "§f" + Emote.GOLD);
                break;
            case 45:
                lore.add("x1 Zombie's Spawner");
                break;
            case 50:
                lore.add("XP multiplicator x1.25");
                break;
            case 55:
                lore.add(formatBigNumber(100000) + "§f" + Emote.GOLD);
                break;
            case 60:
                lore.add("x1 Histerite hoe [Unbreaking II]");
                break;
            case 65:
                lore.add("x1 Skeleton's Spawner");
                break;
            case 70:
                lore.add(formatBigNumber(200000) + "§f" + Emote.GOLD);
                break;
            case 75:
                lore.add("x1 Piglin's Brute Spawner");
                break;
            case 80:
                lore.add("x1 Histerite hoe [Unbreaking III]");
                break;
            case 85:
                lore.add("x1 Slime's Brute Spawner");
                break;
            case 90:
                lore.add("x1 Enderman's Brute Spawner");
                lore.add(formatBigNumber(1) + "§f" + Emote.GOLD+"§e (yes, yes 1)");
                break;
            case 95:
                lore.add(formatBigNumber(1000000) + "§f" + Emote.GOLD);
                break;
            case 100:
                lore.add("Unlock craft of spawner & unlock Phoenix Birth enchant ability");
                break;
        }
        return lore;
    }
}
