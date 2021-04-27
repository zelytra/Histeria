package fr.zelytra.histeria.managers.items;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public enum CustomMaterial {
    ZEUS_LIGHTNING("§e§lZeus's Lightning", "zeus_lightning", 1, Material.PHANTOM_MEMBRANE, ItemType.MISCELLANEOUS);


    private final String displayName;
    private final String name;
    private final int customModelData;
    private final Material vanillaMaterial;
    private final ItemType itemType;

    private EquipmentSlot slot;
    private int armor;
    private int damage;
    private int extraHeart;

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
    }

    CustomMaterial(String displayName, String name, int CMD, Material material, ItemType itemType, int armor, int extraHeart,EquipmentSlot slot) {
        this.displayName = displayName;
        this.name = name;
        this.customModelData = CMD;
        this.vanillaMaterial = material;
        this.itemType = itemType;
        this.armor = armor;
        this.extraHeart = extraHeart;
        this.slot = slot;
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

    public static CustomMaterial getByName(String name) {
        for (CustomMaterial material : CustomMaterial.values()) {
            if (material.getName().equalsIgnoreCase(name)) {
                return material;
            }
        }
        return null;
    }

}
