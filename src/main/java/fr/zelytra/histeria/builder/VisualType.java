package fr.zelytra.histeria.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum VisualType {
    BLANK_BLACK_GLASSE(new VisualItemStack(Material.BLACK_STAINED_GLASS_PANE, " ", false)),
    BLANK_BLUE_GLASSE(new VisualItemStack(Material.BLUE_STAINED_GLASS_PANE, " ", false)),
    BLANK_RED_GLASSE(new VisualItemStack(Material.RED_STAINED_GLASS_PANE, " ", false)),
    BLANK_GREEN_GLASSE(new VisualItemStack(Material.GREEN_STAINED_GLASS_PANE, " ", false)),
    BLANK_GRAY_GLASSE(new VisualItemStack(Material.GRAY_STAINED_GLASS_PANE, " ", false)),
    BLANK_LIGHT_GRAY_GLASSE(new VisualItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", false));

    private final VisualItemStack item;

    VisualType(VisualItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item.getItem();
    }
}
