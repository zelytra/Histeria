package fr.zelytra.histeria.managers.loottable;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public enum LootsEnum {
    TEST(new ItemStack(Material.ACACIA_BOAT));


    private ItemStack item;
    private double luck;
    private PotionEffect potionEffect;

    LootsEnum(ItemStack item, double luck) {
        this.item = item;
        this.luck = luck;
    }

    LootsEnum(ItemStack item) {
        this.item = item;

    }

    LootsEnum(PotionEffect potion) {
        this.potionEffect = potion;
    }

    public ItemStack getItem() {
        return item;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    public double getLuck() {
        return luck;
    }
}
