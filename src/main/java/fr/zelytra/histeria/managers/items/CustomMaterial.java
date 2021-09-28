/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.items;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public enum CustomMaterial {
    HEAL_STICK("§dHealStick", "healstick", "Rend un total de quatre coeurs", 3, Material.STICK, ItemType.MISCELLANEOUS),
    JUMP_STICK("§aJumpStick", "jumpstick", "Applique un effet de Jump Boost III pendant 10 sec", 1, Material.STICK, ItemType.MISCELLANEOUS),
    SPEED_STICK("§bSpeedStick", "speedstick", "Applique un effet de Speed III pendant 10 sec", 2, Material.STICK, ItemType.MISCELLANEOUS),
    BUBBLE_WAND("§bBubble Wand", "bubblewand", "évapore tout liquide dans une zone de 7x7x7", 4, Material.STICK, ItemType.MISCELLANEOUS),
    OBSIDIAN_BREAKER("§5Obsidian Breaker", "obsidian_breaker", "Casse l'obsidian#1 chance sur 8 de casser la Reinforced Obsidian", 32, Material.STICK, ItemType.MISCELLANEOUS),
    CHEST_EXPLORER("§7Chest Explorer", "chest_explorer", "Permet d'ouvrir un coffre dans une zone claim", 31, Material.STICK, ItemType.MISCELLANEOUS),

    COMPRESS_COBBLESTONE("§fCompress Cobblestone", "compress_cobblestone", "Utilisable sur la Crushing Table pour#récuperer des ressources", 9, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    XP_ORB("§aExperience Orb", "xp_orb", "Cet item permet de stoquer#son xp", 19, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    DRAGON_WING("§5Dragon Wings", "dragon_wings", "404 not found", 19, Material.ELYTRA, ItemType.TOOL),
    DYNAMITE("§cDynamite", "dynamite", "Projectile explosif hautement instable", 13, Material.SNOWBALL, ItemType.MISCELLANEOUS),
    SHURIKEN("§7Shuriken", "shuriken", "Projectile discret originaire d'Asie", 14, Material.SNOWBALL, ItemType.MISCELLANEOUS),
    LOTTERY_TICKET("§eLottery Ticket", "lottery_ticket", "Uniquement en vente#Gain d'argent aléatoire", 35, Material.PAPER, ItemType.MISCELLANEOUS),
    ALPIN_HOOK("§bAlpin Hook", "alpin_hook", "Permet l'ascension de toute surface verticale", 39, Material.STONE_PICKAXE, ItemType.TOOL),
    UNCLAIM_FINDER("§9Unclaim Finder", "unclaim_finder", "Cet item vous indique le nombre#de coffres dans zone de 128x128#autour de vous", 40, Material.COMPASS, ItemType.MISCELLANEOUS),

    NOCTURITE_SWORD("§5Nocturite Sword", "nocturite_sword", "Durabilité: 3600#Pouvoir: projette toute entité alentour", 30, Material.GOLDEN_SWORD, ItemType.TOOL, 3600, 12, EquipmentSlot.HAND),
    HISTERITE_SWORD("§cHisterite Sword", "histerite_sword", "Durabilité: 2800", 28, Material.GOLDEN_SWORD, ItemType.TOOL, 2800, 10, EquipmentSlot.HAND),
    HISTERITE_PICKAXE("§cHisterite Pickaxe", "histerite_pickaxe", "Durabilité: 3000#Casse une colonne de deux blocs de haut", 5, Material.NETHERITE_PICKAXE, ItemType.TOOL, 3000),
    HISTERITE_AXE("§cHisterite Axe", "histerite_axe", "Durabilité: 2900#Casse une colonne de bois continue", 41, Material.GOLDEN_AXE, ItemType.TOOL, 2900),
    HISTERITE_SHOVEL("§cHisterite Shovel", "histerite_shovel", "Durabilité: 2900#Creuse une zone plate de 5x5", 38, Material.GOLDEN_SHOVEL, ItemType.TOOL, 2900),
    HISTERITE_HOE("§cHisterite Hoe", "histerite_hoe", "Durabilité: 2900#Laboure une zone de:#- 5x5 (Clic Droit)#- 10x1 (Sneak+Clic Droit)", 37, Material.GOLDEN_HOE, ItemType.TOOL, 2900),
    HAMMER("§6Hammer", "hammer", "Durabilité: 3500#Casse une zone cube de 3x3x3", 6, Material.NETHERITE_PICKAXE, ItemType.TOOL, 3500),

    HISTERITE_NUGGET("§cHisterite Nugget", "histerite_nugget", 47, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    HISTERITE_INGOT("§cHisterite Ingot", "histerite_ingot", 15, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    HISTERITE_CRYSTAL("§cHisterite Crystal", "histerite_crystal", 8, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    HISTERITE_CORE("§cHisterite Core", "histerite_core", 34, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    HISTERITE_STICK("§cHisterite Stick", "histerite_stick", 33, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    HISTERITE_APPLE("§cHisterite Apple", "histerite_apple", 29, Material.GOLDEN_APPLE, ItemType.MISCELLANEOUS),
    RED_MATTER("§4Red Matter", "red_matter", "Allez checker le shop 2 sec...", 50, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),

    NOCTURITE_NUGGET("§5Nocturite Nugget", "nocturite_nugget", "Générée à partir du Nocturite Generator", 48, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    NOCTURITE_INGOT("§5Nocturite Ingot", "nocturite_ingot", 16, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    NOCTURITE_CRYSTAL("§5Nocturite Crystal", "nocturite_crystal", 7, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),
    NOCTURITE_CORE("§5Nocturite Core", "nocturite_core", "Obtenable sur l'Ender Dragon#ou en vente sur la place du marché", 49, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS),

    VOTE_KEY("§aVote Key", "vote_key", "Obtenable via Kit Vote#Utilisable sur la LootBox", 10, Material.GHAST_TEAR, ItemType.MISCELLANEOUS),
    DIAMOND_KEY("§bDiamond Key", "diamond_key", "Obtenable via Kit Lord#Utilisable sur la LootBox", 36, Material.GHAST_TEAR, ItemType.MISCELLANEOUS),
    HISTERITE_KEY("§cHisterite Key", "histerite_key", "Obtenable via Kit Monarch#Utilisable sur la LootBox", 11, Material.GHAST_TEAR, ItemType.MISCELLANEOUS),
    NOCTURITE_KEY("§5Nocturite Key", "nocturite_key", "Obtenable via Kit Demigod#Utilisable sur la LootBox", 12, Material.GHAST_TEAR, ItemType.MISCELLANEOUS),

    NOCTURITE_GENERATOR("nocturite_generator", Material.INFESTED_COBBLESTONE, ItemType.BLOCK),
    ELEVATOR("elevator", Material.INFESTED_CHISELED_STONE_BRICKS, ItemType.BLOCK),
    HISTERITE_BLOCK("histerite_block", Material.PURPUR_BLOCK, ItemType.BLOCK),
    NOCTURITE_BLOCK("nocturite_block", Material.PURPUR_PILLAR, ItemType.BLOCK),
    HISTERITE_ORE("histerite_ore", Material.CHISELED_NETHER_BRICKS, ItemType.BLOCK),
    REINFORCED_OBSIDIAN("reinforced_obsidian", Material.LODESTONE, ItemType.BLOCK),
    LUCKY_BLOCK("lucky_block", Material.HONEYCOMB_BLOCK, ItemType.BLOCK),
    LOOT_BOX("loot_box", Material.END_PORTAL_FRAME, ItemType.BLOCK),
    CAVE_BLOCK("cave_block", Material.CRACKED_NETHER_BRICKS, ItemType.BLOCK),
    CRUSHING_TABLE("crushing_table", Material.FLETCHING_TABLE, ItemType.BLOCK),


    HISTERITE_HELMET("§cHisterite Helmet", "histerite_helmet", "Durabilité: 1600#Effet: Night Vision", 20, Material.LEATHER_HELMET, ItemType.ARMOR, 1600, 5, 4, EquipmentSlot.HEAD, new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false, true)),
    HISTERITE_CHESTPLATE("§cHisterite Chestplate", "histerite_chestplate", "Durabilité: 1800", 22, Material.LEATHER_CHESTPLATE, ItemType.ARMOR, 1700, 10, 6, EquipmentSlot.CHEST),
    HISTERITE_LEGGINGS("§cHisterite Leggings", "histerite_leggings", "Durabilité: 1700#Effet: Speed I", 24, Material.LEATHER_LEGGINGS, ItemType.ARMOR, 1700, 8, 6, EquipmentSlot.LEGS, new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false, true)),
    HISTERITE_BOOTS("§cHisterite Boots", "histerite_boots", "Durabilité: 1500", 26, Material.LEATHER_BOOTS, ItemType.ARMOR, 1500, 5, 4, EquipmentSlot.FEET),

    NOCTURITE_HELMET("§5Nocturite Helmet", "nocturite_helmet", "Durabilité: 3000#Effet: Night Vision | Water Breathing", 21, Material.CHAINMAIL_HELMET, ItemType.ARMOR, 3000, 6, 5, EquipmentSlot.HEAD, new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false, true), new PotionEffect(PotionEffectType.WATER_BREATHING, 999999, 1, false, false, true)),
    NOCTURITE_CHESTPLATE("§5Nocturite Chestplate", "nocturite_chestplate", "Durabilité: 3200#Effet: Résistance", 23, Material.CHAINMAIL_CHESTPLATE, ItemType.ARMOR, 3100, 13, 9, EquipmentSlot.CHEST, new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 0, false, false, true)),
    NOCTURITE_LEGGINGS("§5Nocturite Leggings", "nocturite_leggings", "Durabilité: 3100#Effet: Jump Boost III | Haste", 25, Material.CHAINMAIL_LEGGINGS, ItemType.ARMOR, 3100, 11, 9, EquipmentSlot.LEGS, new PotionEffect(PotionEffectType.SPEED, 999999, 1, false, false, true)),
    NOCTURITE_BOOTS("§5Nocturite Boots", "nocturite_boots", "Durabilité: 2900#Effet: Jump Boost III", 27, Material.CHAINMAIL_BOOTS, ItemType.ARMOR, 2900, 6, 5, EquipmentSlot.FEET),

    NEXT_ARROW("§bNext", "next_arrow",42, Material.POTATO, ItemType.SPECIAL),
    PREVIOUS_ARROW("§bPrevious","previous_arrow",43, Material.BAKED_POTATO, ItemType.SPECIAL),
    SELL_BUTTON("§aSell","sell_button",44, Material.COOKED_MUTTON, ItemType.SPECIAL),
    BUY_BUTTON("§aBuy","buy_button",45, Material.MUTTON, ItemType.SPECIAL),
    VALIDAY("§aConfirm","validay",46, Material.SLIME_BALL, ItemType.SPECIAL);


    private String displayName;
    private String name;
    private int customModelData;
    private final Material vanillaMaterial;
    private final ItemType itemType;

    private EquipmentSlot slot;
    private int armor;
    private int damage;
    private int extraHeart;
    private int durability;
    private String description;
    private PotionEffect[] potions;

    CustomMaterial(String name, Material material, ItemType itemType) {
        this.name = name;
        this.vanillaMaterial = material;
        this.itemType = itemType;
    }

    CustomMaterial(String displayName, String name, int CMD, Material material, ItemType itemType) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
    }

    CustomMaterial(String displayName, String name, String description, int CMD, Material material, ItemType itemType) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
        this.description = description;
    }

    CustomMaterial(String displayName, String name, String description, int CMD, Material material, ItemType itemType, int durability, int armor, int extraHeart, EquipmentSlot slot, PotionEffect... potions) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
        this.armor = armor;
        this.extraHeart = extraHeart;
        this.slot = slot;
        this.description = description;
        this.durability = durability;
        this.potions = potions;
    }

    CustomMaterial(String displayName, String name, String description, int CMD, Material material, ItemType itemType, int durability, int damage, EquipmentSlot slot) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
        this.slot = slot;
        this.description = description;
        this.durability = durability;
        this.damage = damage;
    }

    CustomMaterial(String displayName, String name, String description, int CMD, Material material, ItemType itemType, int durability) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
        this.description = description;
        this.durability = durability;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Material getVanillaMaterial() {
        return vanillaMaterial;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }

    public int getExtraHeart() {
        return extraHeart;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getDescription() {
        return description;
    }

    public PotionEffect[] getPotions() {
        return potions;
    }

    public static List<Material> getCustomBlocks(){
        List<Material> materials = new ArrayList<>();
        for (CustomMaterial material: CustomMaterial.values()) {
            if(material.getItemType()==ItemType.BLOCK){
                materials.add(material.getVanillaMaterial());
            }
        }
        return materials;
    }

    public static CustomMaterial getByName(String name) {
        for (CustomMaterial material : CustomMaterial.values()) {
            if (material.getName() != null && material.getName().equalsIgnoreCase(name)) {
                return material;
            }
        }
        return null;
    }

    public int getDurability() {
        return durability;
    }
}
