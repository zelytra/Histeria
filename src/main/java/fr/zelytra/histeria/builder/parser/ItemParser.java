/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.builder.parser;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemParser {
    private final FileConfiguration config;

    /**
     * Object that return itemStacks list of a config file
     * Required YML format :
     * <p>
     * TableName:
     * itemX:
     * material: {Material|CustomMaterial}
     * amount: {int}
     * enchant:
     * type: {EnchantType}
     * level: {int}
     *
     * @param fileStream YML inside resources plugin
     */

    public ItemParser(InputStream fileStream) {
        Reader reader = new InputStreamReader(fileStream);
        this.config = YamlConfiguration.loadConfiguration(reader);
    }

    public ItemParser(File file) {
        this.config = new YamlConfiguration();

        try {
            this.config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Failed to read the configuration file :");
            e.printStackTrace();
            return;
        }

    }

    public List<ItemStack> getListOf(String tag) {
        List<ItemStack> items = new ArrayList<>();

        for (String loot : config.getConfigurationSection(tag).getKeys(false)) {

            if (config.getString(tag + "." + loot + ".material") == null){
                items.add(new ItemStack(Material.AIR));
                continue;
            }

            Material material = Material.getMaterial(config.getString(tag + "." + loot + ".material").toUpperCase());
            CustomMaterial customMaterial;
            ItemStack item;
            int amount = config.getInt(tag + "." + loot + ".amount");

            /* Item builder */
            if (material == null) {

                customMaterial = CustomMaterial.getByName(config.getString(tag + "." + loot + ".material"));

                if(customMaterial == null){
                    System.out.println("LootParser problem with : " + config.getString(tag + "." + loot + ".material").toUpperCase());
                    continue;
                }

                item = new CustomItemStack(customMaterial, amount).getItem();
            } else {
                item = new ItemStack(material, amount);
            }

            /* Enchant reader */
            if (config.getString(tag + "." + loot + ".enchant.type") != null) {
                if (Enchantment.getByName(config.getString(tag + "." + loot + ".enchant.type").toUpperCase()) == null) {
                    System.out.println("LootParser problem with : " + config.getString(tag + "." + loot + ".enchant.type").toUpperCase());
                    continue;
                }
                if (material != Material.ENCHANTED_BOOK) {
                    ItemMeta meta = item.getItemMeta();
                    meta.addEnchant(Enchantment.getByName(config.getString(tag + "." + loot + ".enchant.type").toUpperCase()), config.getInt(config.getString(tag + "." + loot + ".enchant.level")), true);
                    item.setItemMeta(meta);
                } else {
                    item = bookEnchantedItemStack(material, Enchantment.getByName(config.getString(tag + "." + loot + ".enchant.type").toUpperCase()), config.getInt(tag + "." + loot + ".enchant.level"));
                }
            }

            /* Potion reader */
            if (config.getString(tag + "." + loot + ".effect.type") != null) {
                if (PotionEffectType.getByName(config.getString(tag + "." + loot + ".effect.type").toUpperCase()) == null) {
                    System.out.println("LootParser problem with : " + config.getString(tag + "." + loot + ".effect.type").toUpperCase());
                    continue;
                }
                if (material == Material.POTION || material == Material.SPLASH_POTION) {
                    PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

                    PotionEffectType potionEffectType = PotionEffectType.getByName(config.getString(tag + "." + loot + ".effect.type").toUpperCase());
                    int duration = config.getInt(tag + "." + loot + ".effect.duration") * 20;
                    int amplifier = config.getInt(tag + "." + loot + ".effect.amplifier");

                    potionMeta.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(0, 255 + 1), ThreadLocalRandom.current().nextInt(0, 255 + 1), ThreadLocalRandom.current().nextInt(0, 255 + 1)));
                    potionMeta.addCustomEffect(new PotionEffect(potionEffectType, duration, amplifier), true);
                    item.setItemMeta(potionMeta);
                    ItemMeta meta = item.getItemMeta();
                    meta.displayName(Component.text().content("Potion of " + potionEffectType.getName().toLowerCase()).build());
                    item.setItemMeta(meta);

                } else {
                    System.out.println("LootParser problem : Cannot add potion effect on " + config.getString(tag + "." + loot) + " item type");
                }

            }
            items.add(item);
        }
        return items;
    }

    private ItemStack bookEnchantedItemStack(Material material, Enchantment enchantment, int lvl) {
        ItemStack item = new ItemStack(material);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchantment, lvl, false);
        item.setItemMeta(meta);
        return item;
    }
}
