/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.builder.parser;

import org.bukkit.inventory.ItemStack;

public class ItemLuck {

    public double luck;
    public ItemStack item;

    public ItemLuck(double luck, ItemStack item) {
        this.luck = luck;
        this.item = item;
    }

}
