/*
 * Copyright (c) 2022-2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants.builder;

import fr.zelytra.histeria.utils.RomanNumber;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomEnchant extends Enchantment {

    public final static CustomEnchant BLESS_OF_KEEPING = new CustomEnchant(CustomEnchantData.BLESS_OF_KEEPING);
    public final static CustomEnchant LIGHTNING = new CustomEnchant(CustomEnchantData.LIGHTNING);
    public final static CustomEnchant SHIELD = new CustomEnchant(CustomEnchantData.SHIELD);
    public final static CustomEnchant VAMPIRISME = new CustomEnchant(CustomEnchantData.VAMPIRISME);
    public final static CustomEnchant STUN = new CustomEnchant(CustomEnchantData.STUN);
    public final static CustomEnchant PHOENIX_BIRTH = new CustomEnchant(CustomEnchantData.PHOENIX_BIRTH);

    private static final List<Enchantment> enchants = new ArrayList<>();
    private final CustomEnchantData customEnchantData;

    /**
     * WARNING : if you wanna modificate this class a restart server and not a reloading
     */
    public CustomEnchant(CustomEnchantData customEnchantData) {
        super(customEnchantData.getKey());
        this.customEnchantData = customEnchantData;
    }

    @Override
    public @NotNull String getName() {
        return customEnchantData.name;
    }

    @Override
    public int getMaxLevel() {
        return customEnchantData.maxLvl;
    }

    @Override
    public int getStartLevel() {
        return customEnchantData.minLvl;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return customEnchantData.target;
    }

    @Override
    public boolean isTreasure() {
        return customEnchantData.isTreasure;
    }

    @Override
    public boolean isCursed() {
        return customEnchantData.isCursed;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return customEnchantData.conflicts.contains(other);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return customEnchantData.target.includes(item.getType()) || item.getType() == Material.ENCHANTED_BOOK;
    }

    @Override
    public @NotNull Component displayName(int level) {
        if (customEnchantData.maxLvl <= 1)
            return Component.text().content("§r" + customEnchantData.color + customEnchantData.name).build();
        else
            return Component.text().content("§r" + customEnchantData.color + customEnchantData.name + " " + RomanNumber.toRoman(level)).build();
    }

    @Override
    public boolean isTradeable() {
        return customEnchantData.isTradeable;
    }

    @Override
    public boolean isDiscoverable() {
        return customEnchantData.isDiscoverable;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.VERY_RARE;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return customEnchantData.damageIncrease;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return customEnchantData.activeSlot;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }

    @Override
    public @NotNull Key key() {
        return super.key();
    }

    public static Enchantment[] values() {
        Enchantment[] enchantList = new CustomEnchant[enchants.size()];
        for (int x = 0; x < enchantList.length; x++)
            enchantList[x] = enchants.get(x);
        return enchantList;

    }

    public void register() {
        try {
            enchants.add(this);
            registerEnchantment(this);
        } catch (Exception ignored) {

        }
    }

    public CustomEnchantData getData() {
        return customEnchantData;
    }

}
