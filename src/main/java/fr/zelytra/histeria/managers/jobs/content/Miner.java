package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.builder.Job;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.utils.ExperienceMath;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Miner extends Job implements JobInterface {

    public Miner(int xp) {
        super(xp);
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
    public void setLevel(int level) {
        super.setLevel(level);
    }

    @Override
    public int getXP() {
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
                ItemStack item = Utils.EnchantedItemStack(Material.IRON_PICKAXE, Enchantment.DIG_SPEED, 1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(2500);
                break;
            case 10:
                item = Utils.EnchantedItemStack(Material.IRON_PICKAXE, Enchantment.DIG_SPEED, 2);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(5000);
                break;
            case 15:
                item = Utils.EnchantedItemStack(Material.IRON_PICKAXE, Enchantment.DIG_SPEED, 4);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(10000);
                break;
            case 20:
                Utils.safeGive(player.getPlayer(), new ItemStack(Material.IRON_INGOT, 64));
                player.getBankAccount().addMoney(15000);
                break;
            case 25:
                item = Utils.EnchantedItemStack(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 2);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(20000);
                break;
            case 30:
                Utils.safeGive(player.getPlayer(), new ItemStack(Material.DIAMOND, 32));
                player.getBankAccount().addMoney(25000);
                break;
            case 35:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 15).getItem());
                player.getBankAccount().addMoney(30000);
                break;
            case 40:
                item = Utils.EnchantedItemStack(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 4);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(35000);
                break;
            case 45:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 32).getItem());
                player.getBankAccount().addMoney(37500);
                break;
            case 50:

                break;
            case 55:
                item = new CustomItemStack(CustomMaterial.HISTERITE_PICKAXE, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DIG_SPEED, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 60:
                player.getBankAccount().addMoney(150000);
                break;
            case 65:
                item = new CustomItemStack(CustomMaterial.HISTERITE_PICKAXE, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DIG_SPEED, 5);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(50000);
                break;
            case 70:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 64).getItem());
                break;
            case 75:
                item = Utils.EnchantedItemStack(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 7);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 80:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.NOCTURITE_CORE, 1).getItem());
                break;
            case 85:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.NOCTURITE_INGOT, 2).getItem());
                player.getBankAccount().addMoney(300000);
                break;
            case 90:
                item = new CustomItemStack(CustomMaterial.HAMMER, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DIG_SPEED, 10);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 95:
                player.getBankAccount().addMoney(1000000);
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.NOCTURITE_GENERATOR, 1).getItem());
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
                lore.add("x1 Iron pickaxe [Efficiency I]");
                lore.add(formatBigNumber(2500) + "§f" + Emote.GOLD);
                break;
            case 10:
                lore.add("x1 Iron pickaxe [Efficiency II, Unbreaking II]");
                lore.add(formatBigNumber(5000) + "§f" + Emote.GOLD);
                break;
            case 15:
                lore.add("x1 Iron pickaxe [Efficiency IV, Unbreaking II]");
                lore.add(formatBigNumber(10000) + "§f" + Emote.GOLD);
                break;
            case 20:
                lore.add("x64 Iron ingots");
                lore.add(formatBigNumber(15000) + "§f" + Emote.GOLD);
                break;
            case 25:
                lore.add("x1 Diamond pickaxe [Efficiency II, Unbreaking II]");
                lore.add(formatBigNumber(20000) + "§f" + Emote.GOLD);
                break;
            case 30:
                lore.add("x32 Diamonds");
                lore.add(formatBigNumber(25000) + "§f" + Emote.GOLD);
                break;
            case 35:
                lore.add("x15 Histerite ingots");
                lore.add(formatBigNumber(30000) + "§f" + Emote.GOLD);
                break;
            case 40:
                lore.add("x1 Diamond pickaxe [Efficiency IV, Unbreaking II]");
                lore.add(formatBigNumber(35000) + "§f" + Emote.GOLD);
                break;
            case 45:
                lore.add("x32 Histerite ingots");
                lore.add(formatBigNumber(37500) + "§f" + Emote.GOLD);
                break;
            case 50:
                lore.add("XP multiplicator x1.25");
                break;
            case 55:
                lore.add("x1 Histerite pickaxe [Efficiency II]");
                break;
            case 60:
                lore.add(formatBigNumber(150000) + "§f" + Emote.GOLD);
                break;
            case 65:
                lore.add("x1 Histerite pickaxe [Efficiency V]");
                lore.add(formatBigNumber(50000) + "§f" + Emote.GOLD);
                break;
            case 70:
                lore.add("x64 Histerite ingots");
                break;
            case 75:
                lore.add("x1 Diamond pickaxe [Efficiency VII, Unbreaking II]");
                break;
            case 80:
                lore.add("x1 Nocturite Core");
                break;
            case 85:
                lore.add("x2 Nocturite ingots");
                lore.add(formatBigNumber(300000) + "§f" + Emote.GOLD);
                break;
            case 90:
                lore.add("x1 Hammer [Efficiency X, Unbreaking II]");
                break;
            case 95:
                lore.add(formatBigNumber(1000000) + "§f" + Emote.GOLD);
                break;
            case 100:
                lore.add("Unlock craft of : Core Mining Drill");
                break;
        }
        return lore;
    }

}
