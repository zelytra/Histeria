/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.miningDrill;

import org.bukkit.Material;

import java.io.Serializable;

class OreContainer implements Serializable {

    public final Material ore;
    public final int luck;
    public int count = 0;

    public OreContainer(Material material, int luck) {
        this.ore = material;
        this.luck = luck;
    }

    public void increment() {
        if (count <= ore.getMaxStackSize())
            count++;
    }
}
