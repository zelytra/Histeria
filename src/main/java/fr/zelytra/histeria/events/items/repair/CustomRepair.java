/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.repair;

import fr.zelytra.histeria.managers.items.CustomMaterial;

public enum CustomRepair {
    HISTERITE_PICKAXE(CustomMaterial.HISTERITE_PICKAXE, CustomMaterial.HISTERITE_INGOT, 150),
    HISTERITE_SHOVEL(CustomMaterial.HISTERITE_SHOVEL, CustomMaterial.HISTERITE_INGOT, 150),
    HISTERITE_AXE(CustomMaterial.HISTERITE_AXE, CustomMaterial.HISTERITE_INGOT, 150),
    HISTERITE_HOE(CustomMaterial.HISTERITE_HOE, CustomMaterial.HISTERITE_INGOT, 150),

    HISTERITE_HELMET(CustomMaterial.HISTERITE_HELMET, CustomMaterial.HISTERITE_INGOT, 200),
    HISTERITE_CHESTPLATE(CustomMaterial.HISTERITE_CHESTPLATE, CustomMaterial.HISTERITE_INGOT, 200),
    HISTERITE_LEGGINGS(CustomMaterial.HISTERITE_LEGGINGS, CustomMaterial.HISTERITE_INGOT, 200),
    HISTERITE_BOOTS(CustomMaterial.HISTERITE_BOOTS, CustomMaterial.HISTERITE_INGOT, 200),

    NOCTURITE_HELMET(CustomMaterial.NOCTURITE_HELMET, CustomMaterial.NOCTURITE_INGOT, 200),
    NOCTURITE_CHESTPLATE(CustomMaterial.NOCTURITE_CHESTPLATE, CustomMaterial.NOCTURITE_INGOT, 200),
    NOCTURITE_LEGGINGS(CustomMaterial.NOCTURITE_LEGGINGS, CustomMaterial.NOCTURITE_INGOT, 200),
    NOCTURITE_BOOTS(CustomMaterial.NOCTURITE_BOOTS, CustomMaterial.NOCTURITE_INGOT, 200),

    HISTERITE_SWORD(CustomMaterial.HISTERITE_SWORD, CustomMaterial.HISTERITE_INGOT, 150),
    NOCTURITE_SWORD(CustomMaterial.NOCTURITE_SWORD, CustomMaterial.NOCTURITE_INGOT, 500),
    HAMMER(CustomMaterial.HAMMER, CustomMaterial.NOCTURITE_INGOT, 1000);


    private CustomMaterial item;
    private CustomMaterial material;
    private int value;

    CustomRepair(CustomMaterial item, CustomMaterial material, int value) {
        this.item = item;
        this.material = material;
        this.value = value;
    }

    public CustomMaterial getItem() {
        return item;
    }

    public CustomMaterial getMaterial() {
        return material;
    }

    public int getValue() {
        return value;
    }

    public static CustomRepair getByCustomMaterial(CustomMaterial material) {
        for (CustomRepair repair : CustomRepair.values()) {
            if(repair.getItem()==material){
                return repair;
            }
        }
        return null;
    }

}
