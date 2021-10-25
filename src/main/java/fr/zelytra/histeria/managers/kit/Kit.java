/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.kit;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.parser.ItemParser;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.util.List;

public class Kit {

    private final KitEnum kitEnum;
    private final List<ItemStack> itemList;

    public Kit(KitEnum kitEnum) {

        this.kitEnum = kitEnum;

        InputStream is = Histeria.getInstance().getResource("kit.yml");
        ItemParser parser = new ItemParser(is);
        this.itemList = parser.getListOf(kitEnum.getGroupName());

    }

    public List<ItemStack> getItemList() {
        return itemList;
    }
}
