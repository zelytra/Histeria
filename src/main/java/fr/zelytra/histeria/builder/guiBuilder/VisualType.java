/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.builder.guiBuilder;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum VisualType {
    BLANK_BLACK_GLASSE(new VisualItemStack(Material.BLACK_STAINED_GLASS_PANE, " ", false)),
    BLANK_BLUE_GLASSE(new VisualItemStack(Material.BLUE_STAINED_GLASS_PANE, " ", false)),
    BLANK_CYAN_GLASSE(new VisualItemStack(Material.CYAN_STAINED_GLASS_PANE, " ", false)),
    BLANK_LIGHT_BLUE_GLASSE(new VisualItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ", false)),
    BLANK_RED_GLASSE(new VisualItemStack(Material.RED_STAINED_GLASS_PANE, " ", false)),
    BLANK_GREEN_GLASSE(new VisualItemStack(Material.GREEN_STAINED_GLASS_PANE, " ", false)),
    BLANK_ORANGE_GLASSE(new VisualItemStack(Material.ORANGE_STAINED_GLASS_PANE, " ", false)),
    BLANK_GRAY_GLASSE(new VisualItemStack(Material.GRAY_STAINED_GLASS_PANE, " ", false)),
    BLANK_LIGHT_GRAY_GLASSE(new VisualItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", false)),

    NEXT_ARROW(new VisualItemStack(CustomMaterial.NEXT_ARROW, "§bNext", false)),
    PREVIOUS_ARROW(new VisualItemStack(CustomMaterial.PREVIOUS_ARROW, "§bPrevious", false)),
    BUY(new VisualItemStack(CustomMaterial.BUY_BUTTON, "§bBuy", false)),
    SELL(new VisualItemStack(CustomMaterial.SELL_BUTTON, "§bSell", false)),
    VALIDAY(new VisualItemStack(CustomMaterial.VALIDAY, "§bValide sell", false)),
    RETURN(new VisualItemStack(Material.BARRIER, "§cBack", false,"Back to main page"));

    private final VisualItemStack item;

    VisualType(VisualItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item.getItem();
    }

    public Material getType(){
        return this.item.getItem().getType();
    }
}
