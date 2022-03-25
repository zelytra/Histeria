package fr.zelytra.histeria.managers.jobs.content;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchant;
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

public class Enchanter extends Job implements JobInterface {

    public Enchanter(int xp) {
        super(xp);
    }

    @Override
    public String toString() {
        return "Enchanter : " + level + "lvl | " + xp + "xp | " + getProgression();
    }


    @Override
    public JobType getJob() {
        return JobType.ENCHANTER;
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
        VisualItemStack visualItem = new VisualItemStack(CustomMaterial.BADGE_ENCHANTER, "§6Enchanter", level == 100,
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
                ItemStack item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DIG_SPEED, 3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(2500);
                break;
            case 10:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DURABILITY, 2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(5000);
                break;
            case 15:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL, 3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(10000);
                break;
            case 20:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.DAMAGE_ALL, 5);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(15000);
                break;
            case 25:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.MENDING,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(20000);
                break;
            case 30:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.PROTECTION_ENVIRONMENTAL,4);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(25000);
                break;
            case 35:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.BINDING_CURSE,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(30000);
                break;
            case 40:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.SOUL_SPEED,3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(35000);
                break;
            case 45:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), Enchantment.LOOT_BONUS_BLOCKS,4);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(37500);
                break;
            case 50:

                break;
            case 55:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.STUN,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(45000);
                break;
            case 60:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.STUN,2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(150000);
                break;
            case 65:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.SHIELD,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(50000);
                break;
            case 70:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.LIGHTNING,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(75000);
                break;
            case 75:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.VAMPIRISME,1);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(100000);
                break;
            case 80:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.SHIELD,3);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(150000);
                break;
            case 85:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.LIGHTNING,2);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(300000);
                break;
            case 90:
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.STUN,5);
                Utils.safeGive(player.getPlayer(), item);
                player.getBankAccount().addMoney(350000);
                break;
            case 95:
                player.getBankAccount().addMoney(1000000);
                item = Utils.BookEnchantedItemStack(new ItemStack(Material.ENCHANTED_BOOK), CustomEnchant.BLESS_OF_KEEPING,1);
                Utils.safeGive(player.getPlayer(), item);
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
                lore.add("x1 Enchanted Book [Efficiency III]");
                lore.add(formatBigNumber(2500) + "§f" + Emote.GOLD);
                break;
            case 10:
                lore.add("x1 Enchanted Book [Unbreaking II]");
                lore.add(formatBigNumber(5000) + "§f" + Emote.GOLD);
                break;
            case 15:
                lore.add("x1 Enchanted Book [Sharpness III]");
                lore.add(formatBigNumber(10000) + "§f" + Emote.GOLD);
                break;
            case 20:
                lore.add("x1 Enchanted Book [Sharpness V]");
                lore.add(formatBigNumber(15000) + "§f" + Emote.GOLD);
                break;
            case 25:
                lore.add("x1 Enchanted Book [Mending]");
                lore.add(formatBigNumber(20000) + "§f" + Emote.GOLD);
                break;
            case 30:
                lore.add("x1 Enchanted Book [Protection IV]");
                lore.add(formatBigNumber(25000) + "§f" + Emote.GOLD);
                break;
            case 35:
                lore.add("x1 Enchanted Book [Curse of Binding]");
                lore.add(formatBigNumber(30000) + "§f" + Emote.GOLD);
                break;
            case 40:
                lore.add("x1 Enchanted Book [Soul Speed III]");
                lore.add(formatBigNumber(35000) + "§f" + Emote.GOLD);
                break;
            case 45:
                lore.add("x1 Enchanted Book [Fortune IV]");
                lore.add(formatBigNumber(37500) + "§f" + Emote.GOLD);
                break;
            case 50:
                lore.add("XP multiplicator x1.25");
                break;
            case 55:
                lore.add("x1 Enchanted Book [Stun I]");
                lore.add(formatBigNumber(45000) + "§f" + Emote.GOLD);
                break;
            case 60:
                lore.add("x1 Enchanted Book [Stun II]");
                lore.add(formatBigNumber(150000) + "§f" + Emote.GOLD);
                break;
            case 65:
                lore.add("x1 Enchanted Book [Shield I]");
                lore.add(formatBigNumber(50000) + "§f" + Emote.GOLD);
                break;
            case 70:
                lore.add("x1 Enchanted Book [Lightning I]");
                lore.add(formatBigNumber(75000) + "§f" + Emote.GOLD);
                break;
            case 75:
                lore.add("x1 Enchanted Book [Vampirisme]");
                lore.add(formatBigNumber(100000) + "§f" + Emote.GOLD);
                break;
            case 80:
                lore.add("x1 Enchanted Book [Shield III]");
                lore.add(formatBigNumber(150000) + "§f" + Emote.GOLD);
                break;
            case 85:
                lore.add("x1 Enchanted Book [Lightning II]");
                lore.add(formatBigNumber(300000) + "§f" + Emote.GOLD);
                break;
            case 90:
                lore.add("x1 Enchanted Book [Stun V]");
                lore.add(formatBigNumber(350000) + "§f" + Emote.GOLD);
                break;
            case 95:
                lore.add("x1 Enchanted Book [Bless of Keeping]");
                lore.add(formatBigNumber(1000000) + "§f" + Emote.GOLD);
                break;
            case 100:
                lore.add("Unlock possibility of getting custom enchant in enchanting table");
                break;
        }
        return lore;
    }
}
