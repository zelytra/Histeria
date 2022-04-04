/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils;

import fr.zelytra.histeria.Histeria;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class Utils {

    public static List<String> dynamicTab(List<String> list, String arg) {
        List<String> finalList = new ArrayList<>(list);
        for (String s : list) {
            if (!s.toLowerCase().startsWith(arg.toLowerCase())) {
                finalList.remove(s);
            }
        }
        Collections.sort(finalList);
        return finalList;
    }

    public static boolean isFood(Material type) {
        List<Material> food = new ArrayList<>();
        food.add(Material.COOKED_BEEF);
        food.add(Material.COOKED_CHICKEN);
        food.add(Material.COOKED_COD);
        food.add(Material.COOKED_MUTTON);
        food.add(Material.COOKED_PORKCHOP);
        food.add(Material.COOKED_RABBIT);
        food.add(Material.COOKED_SALMON);
        food.add(Material.BEEF);
        food.add(Material.CHICKEN);
        food.add(Material.COD);
        food.add(Material.MUTTON);
        food.add(Material.PORKCHOP);
        food.add(Material.RABBIT);
        food.add(Material.SALMON);

        return food.contains(type);


    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException ignored) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException ignored) {
            return false;
        }
        return true;
    }

    public static int getEmptySlots(Player player) {
        int i = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (!(is == null))
                continue;
            i++;
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (!(item == null))
                continue;
            i--;
        }
        return i;
    }

    public static ItemStack EnchantedItemStack(Material material, Enchantment enchantment, int lvl) {
        ItemStack item = new ItemStack(material);
        item.addUnsafeEnchantment(enchantment, lvl);
        return item;
    }

    public static ItemStack BookEnchantedItemStack(ItemStack item, Enchantment enchantment, int lvl) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchantment, lvl, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack EnchantedItemStack(ItemStack item, Enchantment enchantment, int lvl) {
        item.addUnsafeEnchantment(enchantment, lvl);
        return item;
    }

    public static boolean isFullInv(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            return true;
        }
        return false;
    }

    public static boolean canByPass(Player player) {
        User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        switch (user.getPrimaryGroup()) {
            case "fondateur":
            case "moderator":
            case "administrator":
                return true;

            default:
                return false;
        }

    }

    public static boolean canByPass(String group) {
        switch (group) {
            case "fondateur":
            case "moderator":
            case "administrator":
                return true;

            default:
                return false;
        }

    }

    public static boolean isForbiddenArmorPiece(ItemStack armor) {
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.LEATHER_BOOTS);
        materials.add(Material.LEATHER_CHESTPLATE);
        materials.add(Material.LEATHER_LEGGINGS);
        materials.add(Material.LEATHER_HELMET);
        materials.add(Material.CHAINMAIL_BOOTS);
        materials.add(Material.CHAINMAIL_CHESTPLATE);
        materials.add(Material.CHAINMAIL_LEGGINGS);
        materials.add(Material.CHAINMAIL_HELMET);

        return materials.contains(armor.getType());

    }

    public static void safeGive(Player player, ItemStack item) {
        if (getEmptySlots(player) < 2)
            player.getWorld().dropItem(player.getLocation(), item);
        else
            player.getInventory().addItem(item);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

    public static String formatBigNumber(int value) {
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(bd.longValue());
    }

}
