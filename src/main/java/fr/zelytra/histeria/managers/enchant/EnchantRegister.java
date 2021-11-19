/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.enchant;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;

public abstract class EnchantRegister {

    public static final Enchantment enchant = new BlessOfKeeping();

    public static void registerEnchants(){
        try {

            Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            fieldAcceptingNew.setAccessible(true);
            fieldAcceptingNew.set(null,true);
            fieldAcceptingNew.setAccessible(false);


            Enchantment.registerEnchantment(enchant);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
