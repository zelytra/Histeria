/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.shop;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerShop implements Listener {

    private Player player;
    private int pageNumber = 0;
    private ShopFilter filter = ShopFilter.NONE;
    private InterfaceBuilder interfaceBuilder;

    public PlayerShop(Player player) {
        this.player = player;

        interfaceBuilder = new InterfaceBuilder(54, "§6Shop");
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
        interfaceBuilder.open(this.player);

    }

    public void nextPage() {
        if (pageNumber < Histeria.shop.getMaxPageNumber(filter))
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
    }

    public void previousPage() {
        if (pageNumber-- > 0)
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
    }

    public void openItemBuyPage() {
    }




}
