package fr.zelytra.histeria.managers.enchants.builder;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomEnchantUtils {

    public static void updateCustomEnchant(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        List<String> lore;
        if (meta.getLore() != null)
            lore = meta.getLore();
        else
            lore = new ArrayList<>();


        // Creating custom enchant lore
        List<String> newLore = new ArrayList<>();
        if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
            for (var enchant : ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().entrySet()) {
                if (isCustom(enchant.getKey())) {
                    newLore.add(PlainTextComponentSerializer.plainText().serialize(enchant.getKey().displayName(enchant.getValue())));
                }
            }
        } else {
            for (var enchant : item.getEnchantments().entrySet()) {
                if (isCustom(enchant.getKey())) {
                    newLore.add(PlainTextComponentSerializer.plainText().serialize(enchant.getKey().displayName(enchant.getValue())));
                }
            }
        }
        newLore.add("");

        // Adding old lore without old custom enchant lore
        boolean isEnchant = true;
        for (String line : lore) {
            if (isEnchant) {
                if (line.equalsIgnoreCase(""))
                    isEnchant = false;
                continue;
            } else
                newLore.add(line);
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);

    }

    public static boolean isCustom(Enchantment enchant) {
        for (CustomEnchantData data : CustomEnchantData.values())
            if (data.getKey().equals(enchant.getKey()))
                return true;
        return false;
    }

    public static boolean hasCustomEnchants(ItemStack item) {
        if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
            for (var enchant : ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().entrySet())
                if (isCustom(enchant.getKey()))
                    return true;
        } else {
            for (var enchant : item.getEnchantments().entrySet())
                if (isCustom(enchant.getKey()))
                    return true;
        }
        return false;
    }

    public static boolean contain(ItemStack item, CustomEnchant enchant) {
        if (hasCustomEnchants(item))
            for (var index : item.getEnchantments().entrySet())
                if (index.getKey().getKey().equals(enchant.getKey()))
                    return true;
        return false;
    }

    public static int getLevel(ItemStack item, CustomEnchant enchant) {
        if (hasCustomEnchants(item))
            for (var index : item.getEnchantments().entrySet())
                if (index.getKey().getKey().equals(enchant.getKey()))
                    return index.getValue();
        return 1;
    }
}
