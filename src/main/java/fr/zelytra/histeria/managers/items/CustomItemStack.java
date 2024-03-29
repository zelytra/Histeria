/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.items;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.enchants.builder.CustomEnchantUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class CustomItemStack {
    private final static NamespacedKey itemKey = new NamespacedKey(Histeria.getInstance(), "itemName");
    private final static NamespacedKey descriptionKey = new NamespacedKey(Histeria.getInstance(), "description");
    private final static NamespacedKey durabilityKey = new NamespacedKey(Histeria.getInstance(), "durability");

    private ItemStack item;
    private CustomMaterial customMaterial;

    /**
     * @param material Custom material of the item
     * @param amount   Item amount
     */
    public CustomItemStack(@NotNull CustomMaterial material, int amount) {
        this.customMaterial = material;

        switch (material.getItemType()) {
            case BLOCK -> this.item = new ItemStack(material.getVanillaMaterial(), amount);
            case ENCHANT -> this.item = new ItemStack(Material.ENCHANTED_BOOK);
            default -> this.item = new ItemStack(this.customMaterial.getVanillaMaterial(), amount);
        }

        ItemMeta meta = this.item.getItemMeta();
        assert meta != null;
        meta.setCustomModelData(this.customMaterial.getCustomModelData());
        meta.setDisplayName(this.customMaterial.getDisplayName());

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(itemKey, PersistentDataType.STRING, this.customMaterial.getName());

        if (this.customMaterial.getDescription() != null) {
            itemData.set(descriptionKey, PersistentDataType.STRING, this.customMaterial.getDescription());
        }

        switch (material.getItemType()) {
            case ARMOR:
                //meta.setUnbreakable(true);
                //meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeGenerator.armor(material.getArmor(), material.getSlot()));
                meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, AttributeGenerator.extraHeart(material.getExtraHeart(), material.getSlot()));
                itemData.set(durabilityKey, PersistentDataType.INTEGER, this.customMaterial.getDurability());
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("§bDurability §l>§r§f" + this.customMaterial.getDurability() + "/" + this.customMaterial.getDurability());
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;
            case TOOL:
                //meta.setUnbreakable(true);
                //meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                if (this.customMaterial.getDamage() != 0) {
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, AttributeGenerator.attack(material.getDamage(), material.getSlot()));
                }
                if (this.customMaterial.getDurability() != 0) {
                    itemData.set(durabilityKey, PersistentDataType.INTEGER, this.customMaterial.getDurability());
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add("§bDurability §l>§r§f" + this.customMaterial.getDurability() + "/" + this.customMaterial.getDurability());
                    meta.setLore(lore);
                }
                item.setItemMeta(meta);
                break;

            case ENCHANT:
                EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) meta;
                enchantmentStorageMeta.addStoredEnchant(customMaterial.getEnchant(), customMaterial.getEnchant().getMaxLevel(), false);
                item.setItemMeta(enchantmentStorageMeta);
                CustomEnchantUtils.updateCustomEnchant(item);
                break;

            default:
                item.setItemMeta(meta);
                break;


        }


    }

    public ItemStack getItem() {
        return item;
    }

    public static boolean hasTag(ItemStack item) {
        if (item != null && item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer itemData = meta.getPersistentDataContainer();
            return itemData.has(itemKey, PersistentDataType.STRING);
        } else {
            return false;
        }
    }

    public static boolean hasTag(ItemStack item, CustomMaterial customMaterial) {

        if (CustomItemStack.hasTag(item)) {
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            PersistentDataContainer itemData = meta.getPersistentDataContainer();
            return Objects.requireNonNull(itemData.get(itemKey, PersistentDataType.STRING)).equalsIgnoreCase(customMaterial.getName());
        }

        return false;
    }

    public static boolean isCustomBlock(ItemStack item) {
        if (CustomMaterial.getCustomBlocks().contains(item.getType())) {
            return true;
        }
        return false;
    }


    public static boolean hasCustomItemInMainHand(String name, Player player) {
        player.getInventory().getItemInMainHand();
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            if (CustomItemStack.hasTag(player.getInventory().getItemInMainHand())) {
                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                assert meta != null;
                PersistentDataContainer itemData = meta.getPersistentDataContainer();
                return Objects.requireNonNull(itemData.get(itemKey, PersistentDataType.STRING)).equalsIgnoreCase(name);
            }
        }
        return false;
    }

    public static boolean hasCustomItemInOffHand(String name, Player player) {
        player.getInventory().getItemInOffHand();
        if (player.getInventory().getItemInOffHand().getType() != Material.AIR) {
            if (CustomItemStack.hasTag(player.getInventory().getItemInOffHand())) {
                ItemMeta meta = player.getInventory().getItemInOffHand().getItemMeta();
                assert meta != null;
                PersistentDataContainer itemData = meta.getPersistentDataContainer();
                return Objects.requireNonNull(itemData.get(itemKey, PersistentDataType.STRING)).equalsIgnoreCase(name);
            }
        }
        return false;
    }

    public static CustomMaterial getCustomMaterial(ItemStack item) {
        if (!hasTag(item)) {
            return null;
        }
        return CustomMaterial.getByName(item.getItemMeta().getPersistentDataContainer().get(CustomItemStack.getItemKey(), PersistentDataType.STRING));
    }

    public static CustomMaterial getCustomMaterial(String name) {

        for (CustomMaterial material : CustomMaterial.values())
            if (material.getName() != null && material.getName().equalsIgnoreCase(name))
                return material;

        return null;
    }

    public static Material getCustomBlockMaterial(ItemStack item) {

        for (Material material : CustomMaterial.getCustomBlocks())
            if (material == item.getType())
                return material;

        return null;
    }

    public static NamespacedKey getDescriptionKey() {
        return descriptionKey;
    }

    public static NamespacedKey getDurabilityKey() {
        return durabilityKey;
    }

    public static NamespacedKey getItemKey() {
        return itemKey;
    }
}
