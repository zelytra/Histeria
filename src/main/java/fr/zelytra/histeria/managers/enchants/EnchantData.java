/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;

import java.util.List;

public class EnchantData {

    public String name;
    public int minLVL, maxLVL;
    public boolean isTreasure, isCursed;

    public List<EnchantmentTarget> itemsTarget;
    public List<Enchantment> conflicts;



}
