package fr.zelytra.histeria.managers.loottable;


import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum LootsEnum {
    CC_OBSIDIAN(new ItemStack(Material.OBSIDIAN),20),
    CC_DIAMOND(new ItemStack(Material.DIAMOND),10),
    CC_IRON(new ItemStack(Material.IRON_INGOT),20),
    CC_HISTERITE(new CustomItemStack(CustomMaterial.HISTERITE_INGOT,1).getItem(),2),
    CC_GOLD(new ItemStack(Material.GOLD_INGOT),10);


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
