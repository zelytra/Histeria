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
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

public enum CustomEnchantData {

    LIGHTNING("Lightning", "lightning", 1, 2,
            ChatColor.BLUE,
            EnchantmentTarget.WEAPON,
            new EnchantRarity(24,4,EnchantmentRarity.VERY_RARE.getWeight(),30),
            new ArrayList<>(),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND)),
            0, false, false, false, false),

    BLESS_OF_KEEPING("Bless Of Keeping", "bless_of_keeping", 1, 1,
            ChatColor.DARK_PURPLE,
            EnchantmentTarget.WEAPON,
            new EnchantRarity(30,0,EnchantmentRarity.VERY_RARE.getWeight(),30),
            new ArrayList<>(),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND,EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)),
            0, false, false, false, false),

    STUN("Stun", "stun", 1, 5,
            ChatColor.GRAY,
            EnchantmentTarget.WEAPON,
            new EnchantRarity(1,5,EnchantmentRarity.UNCOMMON.getWeight(),30),
            new ArrayList<>(),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND)),
            0, false, false, false, false),

    VAMPIRISME("Vampirisme", "vampirisme", 1, 1,
            ChatColor.DARK_RED,
            EnchantmentTarget.WEAPON,
            new EnchantRarity(30,0,EnchantmentRarity.VERY_RARE.getWeight(),30),
            new ArrayList<>(),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND)),
            0, false, false, false, false),

    PHOENIX_BIRTH("Pheonix Birth", "pheonix_birth", 1, 1,
            ChatColor.DARK_RED,
            EnchantmentTarget.WEAPON,
            new EnchantRarity(30,0,EnchantmentRarity.VERY_RARE.getWeight(),30),
            new ArrayList<>(),
            new HashSet<>(Arrays.asList(EquipmentSlot.HAND)),
            0, false, false, false, false),

    SHIELD("Shield", "shield", 1, 3,
            ChatColor.AQUA,
            EnchantmentTarget.WEARABLE,
            new EnchantRarity(16,3,EnchantmentRarity.VERY_RARE.getWeight(),30),
            new ArrayList<>(Arrays.asList(Enchantment.THORNS)),
            new HashSet<>(Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)),
            0, false, false, false, false);

    //TODO Cursed of fucked up (clear all enchant and lock forever then enchant of the item)


    public String name;
    private String tag;
    public int maxLvl, minLvl;

    public ChatColor color;
    public EnchantmentTarget target;
    public EnchantRarity enchantRarity;


    public List<Enchantment> conflicts;
    public Set<EquipmentSlot> activeSlot;

    public float damageIncrease;
    public boolean isTreasure, isCursed, isTradeable, isDiscoverable;

    CustomEnchantData(String name, String tag, int minLvl, int maxLvl, ChatColor color, EnchantmentTarget target, EnchantRarity rarity, List<Enchantment> conflicts, Set<EquipmentSlot> activeSlot, float damageIncrease, boolean isTreasure, boolean isCursed, boolean isTradeable, boolean isDiscoverable) {
        this.name = name;
        this.tag = tag;
        this.maxLvl = maxLvl;
        this.minLvl = minLvl;
        this.color = color;
        this.target = target;
        this.enchantRarity = rarity;
        this.conflicts = conflicts;
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
