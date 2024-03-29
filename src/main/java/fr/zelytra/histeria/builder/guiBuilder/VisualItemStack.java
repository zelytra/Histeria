/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.builder.guiBuilder;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VisualItemStack {
    private ItemStack item;

    public VisualItemStack(Material material, String name, boolean isEnchanted, String... subMessage) {
        this.item = new ItemStack(material);
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(subMessage));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        if (isEnchanted)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
    }

    public VisualItemStack(CustomMaterial material, String name, boolean isEnchanted, String... subMessage) {
        this.item = new CustomItemStack(material, 1).getItem();
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(subMessage));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        if (isEnchanted)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
    }

    public VisualItemStack(Material material, String name, boolean isEnchanted) {
        this.item = new ItemStack(material);
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        if (isEnchanted)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
    }

    public VisualItemStack(CustomMaterial material, String name, boolean isEnchanted) {
        this.item = new CustomItemStack(material, 1).getItem();
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        if (isEnchanted)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
    }

    public ItemStack getItem() {
        return item;
    }

    public void addAmount() {
        this.item.setAmount(this.item.getAmount() + 1);
    }

    public void removeAmount() {
        if (this.item.getAmount() - 1 > 1) {
            this.item.setAmount(this.item.getAmount() - 1);
        }
    }
}
