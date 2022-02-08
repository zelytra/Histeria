/*
 * Copyright (c) 2022-2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.builder;

import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

public enum CustomEnchantData {
    BLESS_OF_KEEPING("Bless Of Keeping", "bless_of_keeping", 1, 1,
            ChatColor.DARK_PURPLE,
            EnchantmentTarget.WEAPON,
            EnchantmentRarity.COMMON,
            new ArrayList<>(Arrays.asList(Enchantment.ARROW_DAMAGE)),
            new ArrayList<>(Arrays.asList(Material.NETHERITE_SWORD, Material.GOLDEN_SWORD)),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND)),
            0, false, false, false, false);


    public String name;
    private String tag;
    public int maxLvl, minLvl;

    public ChatColor color;
    public EnchantmentTarget target;
    public EnchantmentRarity rarity;

    public List<Enchantment> conflicts;
    public List<Material> enchantItem;
    public Set<EquipmentSlot> activeSlot;

    public float damageIncrease;
    public boolean isTreasure, isCursed, isTradeable, isDiscoverable;

    CustomEnchantData(String name, String tag, int minLvl, int maxLvl, ChatColor color, EnchantmentTarget target, EnchantmentRarity rarity, List<Enchantment> conflicts, List<Material> enchantItem, Set<EquipmentSlot> activeSlot, float damageIncrease, boolean isTreasure, boolean isCursed, boolean isTradeable, boolean isDiscoverable) {
        this.name = name;
        this.tag = tag;
        this.maxLvl = maxLvl;
        this.minLvl = minLvl;
        this.color = color;
        this.target = target;
        this.rarity = rarity;
        this.conflicts = conflicts;
        this.enchantItem = enchantItem;
        this.activeSlot = activeSlot;
        this.damageIncrease = damageIncrease;
        this.isTreasure = isTreasure;
        this.isCursed = isCursed;
        this.isTradeable = isTradeable;
        this.isDiscoverable = isDiscoverable;
    }

    public NamespacedKey getKey() {
        return NamespacedKey.minecraft(tag);
    }
}
