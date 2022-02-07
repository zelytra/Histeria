/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants;

import fr.zelytra.histeria.utils.RomanNumber;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CustomEnchant extends Enchantment {

    private final CustomEnchantData customEnchantData;

    public CustomEnchant(CustomEnchantData customEnchantData) {
        super(customEnchantData.getKey());
        this.customEnchantData = customEnchantData;
        try {
            registerEnchantment(this);
        }catch (Exception ignored){

        }

        System.out.println(customEnchantData.name + " " + isAcceptingRegistrations());
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
        System.out.println("trigger");
        return customEnchantData.enchantItem.contains(item.getType());
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text().content(customEnchantData.name + RomanNumber.toRoman(level)).build();
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
        return customEnchantData.rarity;
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

}
