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

public class Fighter extends Job implements JobInterface {

    public Fighter(int xp) {
        super(xp);
    }

    @Override
    public String toString() {
        return "Fighter : " + level + "lvl | " + xp + "xp | " + getProgression();
    }


    @Override
    public JobType getJob() {
        return JobType.FIGHTER;
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
        VisualItemStack visualItem = new VisualItemStack(CustomMaterial.BADGE_FIGHTER, "§6Fighter", level == 100,
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
                ItemStack item = Utils.EnchantedItemStack(Material.IRON_SWORD, Enchantment.DAMAGE_ALL, 1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(2500);
                break;
            case 10:
                item = Utils.EnchantedItemStack(Material.IRON_SWORD, Enchantment.DAMAGE_ALL, 2);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(5000);
                break;
            case 15:
                item = Utils.EnchantedItemStack(Material.IRON_SWORD, Enchantment.DAMAGE_ALL, 4);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(10000);
                break;
            case 20:
                Utils.safeGive(player.getPlayer(), new CustomItemStack(CustomMaterial.DYNAMITE, 16).getItem());
                player.getBankAccount().addMoney(15000);
                break;
            case 25:
                item = Utils.EnchantedItemStack(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 2);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(20000);
                break;
            case 30:
                item = Utils.EnchantedItemStack(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 3);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(25000);
                break;
            case 35:
                item = Utils.EnchantedItemStack(Material.NETHERITE_SWORD, Enchantment.DAMAGE_ALL, 2);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(30000);
                break;
            case 40:
                item = Utils.EnchantedItemStack(Material.NETHERITE_SWORD, Enchantment.DAMAGE_ALL, 4);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(35000);
                break;
            case 45:
                item = Utils.EnchantedItemStack(Material.NETHERITE_SWORD, Enchantment.DAMAGE_ALL, 5);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(37500);
                break;
            case 50:

                break;
            case 55:
                item = new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DAMAGE_ALL, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 60:
                player.getBankAccount().addMoney(150000);
                break;
            case 65:
                item = new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DAMAGE_ALL, 3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(50000);
                break;
            case 70:
                item = new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DAMAGE_ALL, 4);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                break;
            case 75:
                item = new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem();
                item = Utils.EnchantedItemStack(item, Enchantment.DAMAGE_ALL, 5);
                item = Utils.EnchantedItemStack(item, Enchantment.DURABILITY, 3);
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
                player.getBankAccount().addMoney(500000);
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
                lore.add("x1 Iron Sword [Sharpness I]");
                lore.add(formatBigNumber(2500) + "§f" + Emote.GOLD);
                break;
            case 10:
                lore.add("x1 Iron Sword [Sharpness II, Unbreaking II]");
                lore.add(formatBigNumber(5000) + "§f" + Emote.GOLD);
                break;
            case 15:
                lore.add("x1 Iron Sword [Sharpness IV, Unbreaking II]");
                lore.add(formatBigNumber(10000) + "§f" + Emote.GOLD);
                break;
            case 20:
                lore.add("x16 Dynamites");
                lore.add(formatBigNumber(15000) + "§f" + Emote.GOLD);
                break;
            case 25:
                lore.add("x1 Diamond Sword [Sharpness II, Unbreaking II]");
                lore.add(formatBigNumber(20000) + "§f" + Emote.GOLD);
                break;
            case 30:
                lore.add("x1 Diamond Sword [Sharpness III, Unbreaking II]");
                lore.add(formatBigNumber(25000) + "§f" + Emote.GOLD);
                break;
            case 35:
                lore.add("x1 Netherite Sword [Sharpness II, Unbreaking I]");
                lore.add(formatBigNumber(30000) + "§f" + Emote.GOLD);
                break;
            case 40:
                lore.add("x1 Netherite Sword [Sharpness IV, Unbreaking II]");
                lore.add(formatBigNumber(35000) + "§f" + Emote.GOLD);
                break;
            case 45:
                lore.add("x1 Netherite Sword [Sharpness V, Unbreaking III]");
                lore.add(formatBigNumber(37500) + "§f" + Emote.GOLD);
                break;
            case 50:
                lore.add("XP multiplicator x1.25");
                break;
            case 55:
                lore.add("x1 Histerite Sword [Sharpness II]");
                break;
            case 60:
                lore.add(formatBigNumber(150000) + "§f" + Emote.GOLD);
                break;
            case 65:
                lore.add("x1 Histerite Sword [Sharpness III]");
                lore.add(formatBigNumber(50000) + "§f" + Emote.GOLD);
                break;
            case 70:
                lore.add("x1 Histerite Sword [Sharpness IV, Unbreaking II]");
                break;
            case 75:
                lore.add("x1 Histerite Sword [Sharpness V, Unbreaking III]");
                break;
            case 80:
                lore.add("x1 Nocturite Core");
                break;
            case 85:
                lore.add("x2 Nocturite ingots");
                lore.add(formatBigNumber(300000) + "§f" + Emote.GOLD);
                break;
            case 90:
                lore.add(formatBigNumber(500000) + "§f" + Emote.GOLD);
                break;
            case 95:
                lore.add(formatBigNumber(1000000) + "§f" + Emote.GOLD);
                break;
            case 100:
                lore.add("Unlock craft of : Anti-Nocturite Sword");
                break;
        }
        return lore;
    }
}
