/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.loottable;


import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum LootsEnum {
    /* Cobble stone crusher */
    CC_OBSIDIAN(new ItemStack(Material.OBSIDIAN), 20),
    CC_DIAMOND(new ItemStack(Material.DIAMOND), 10),
    CC_IRON(new ItemStack(Material.IRON_INGOT), 20),
    CC_HISTERITE(new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 1).getItem(), 2),
    CC_GOLD(new ItemStack(Material.GOLD_INGOT), 10),


    /* LootBox */
    VOTEK_SPEED_STICK(new CustomItemStack(CustomMaterial.SPEED_STICK, 1).getItem(), 8),
    VOTEK_JUMP_STICK(new CustomItemStack(CustomMaterial.JUMP_STICK, 1).getItem(), 12),
    VOTEK_HEAL_STICK(new CustomItemStack(CustomMaterial.HEAL_STICK, 1).getItem(), 11),
    VOTEK_BUBBLE_WAND(new CustomItemStack(CustomMaterial.BUBBLE_WAND, 1).getItem(), 16),
    VOTEK_COMPRESS_COBBLESTONE(new CustomItemStack(CustomMaterial.COMPRESS_COBBLESTONE, 2).getItem(), 10),
    VOTEK_IRON_INGOT(new ItemStack(Material.IRON_INGOT, 4), 14),
    VOTEK_DIAMOND(new ItemStack(Material.DIAMOND, 1), 13),
    VOTEK_OBSIDIAN(new ItemStack(Material.OBSIDIAN, 4), 15),
    VOTEK_HISTERITE_NUGGET(new CustomItemStack(CustomMaterial.HISTERITE_NUGGET, 2).getItem(), 8),

    DIAMONDK_SPEED_STICK(new CustomItemStack(CustomMaterial.SPEED_STICK, 2).getItem(), 8),
    DIAMONDK_JUMP_STICK(new CustomItemStack(CustomMaterial.JUMP_STICK, 2).getItem(), 12),
    DIAMONDK_HEAL_STICK(new CustomItemStack(CustomMaterial.HEAL_STICK, 2).getItem(), 11),
    DIAMONDK_BUBBLE_WAND(new CustomItemStack(CustomMaterial.BUBBLE_WAND, 2).getItem(), 16),
    DIAMONDK_COMPRESS_COBBLESTONE(new CustomItemStack(CustomMaterial.COMPRESS_COBBLESTONE, 8).getItem(), 10),
    DIAMONDK_IRON_INGOT(new ItemStack(Material.IRON_INGOT, 8), 14),
    DIAMONDK_DIAMOND(new ItemStack(Material.DIAMOND, 2), 13),
    DIAMONDK_OBSIDIAN(new ItemStack(Material.OBSIDIAN, 8), 15),
    DIAMONDK_HISTERITE_INGOT(new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 2).getItem(), 8),

    HISTERITEK_SPEED_STICK(new CustomItemStack(CustomMaterial.SPEED_STICK, 4).getItem(), 8),
    HISTERITEK_JUMP_STICK(new CustomItemStack(CustomMaterial.JUMP_STICK, 4).getItem(), 12),
    HISTERITEK_HEAL_STICK(new CustomItemStack(CustomMaterial.HEAL_STICK, 4).getItem(), 11),
    HISTERITEK_BUBBLE_WAND(new CustomItemStack(CustomMaterial.BUBBLE_WAND, 4).getItem(), 16),
    HISTERITEK_COMPRESS_COBBLESTONE(new CustomItemStack(CustomMaterial.COMPRESS_COBBLESTONE, 32).getItem(), 10),
    HISTERITEK_IRON_INGOT(new ItemStack(Material.IRON_INGOT, 16), 14),
    HISTERITEK_DIAMOND(new ItemStack(Material.DIAMOND, 4), 13),
    HISTERITEK_OBSIDIAN(new ItemStack(CustomMaterial.REINFORCED_OBSIDIAN.getVanillaMaterial(), 8), 15),
    HISTERITEK_HISTERITE_INGOT(new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 8).getItem(), 8),

    NOCTURITEK_SPEED_STICK(new CustomItemStack(CustomMaterial.SPEED_STICK, 8).getItem(), 8),
    NOCTURITEK_JUMP_STICK(new CustomItemStack(CustomMaterial.JUMP_STICK, 8).getItem(), 12),
    NOCTURITEK_HEAL_STICK(new CustomItemStack(CustomMaterial.HEAL_STICK, 8).getItem(), 11),
    NOCTURITEK_BUBBLE_WAND(new CustomItemStack(CustomMaterial.BUBBLE_WAND, 8).getItem(), 16),
    NOCTURITEK_COMPRESS_COBBLESTONE(new CustomItemStack(CustomMaterial.COMPRESS_COBBLESTONE, 64).getItem(), 10),
    NOCTURITEK_IRON_INGOT(new ItemStack(Material.IRON_INGOT, 32), 14),
    NOCTURITEK_DIAMOND(new ItemStack(Material.DIAMOND, 16), 13),
    NOCTURITEK_OBSIDIAN(new ItemStack(CustomMaterial.REINFORCED_OBSIDIAN.getVanillaMaterial(), 16), 15),
    NOCTURITEK_HISTERITE_INGOT(new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 16).getItem(), 8);


    private ItemStack item;
    private double luck;

    LootsEnum(ItemStack item, double luck) {
        this.item = item;
        this.luck = luck;
    }

    LootsEnum(ItemStack item) {
        this.item = item;

    }

    public ItemStack getItem() {
        return item;
    }

    public double getLuck() {
        return luck;
    }
    }
