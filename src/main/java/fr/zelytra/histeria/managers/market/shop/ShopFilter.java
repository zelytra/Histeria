/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.shop;

import org.bukkit.Material;

public enum ShopFilter {

    BLOCK("block", Material.GRASS_BLOCK),
    ORE("ore", Material.DIAMOND_ORE),
    CUSTOM_ITEM("customItem", Material.GRASS_BLOCK),
    ITEM("item", Material.STICK),
    NONE("", Material.AIR);

    private String dbName;
    private Material material;

    ShopFilter(String dbName, Material material) {
        this.dbName = dbName;
        this.material = material;
    }

    public static ShopFilter getByName(String name) {

        for (ShopFilter filter : ShopFilter.values())
            if (filter.dbName.equalsIgnoreCase(name))
                return filter;

        return NONE;
    }

    public Material getMaterial() {
        return material;
    }
}
