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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerShop implements Listener {
    private static List<PlayerShop> openShop = new ArrayList<>();

    private String shopName = "§6Shop : ";
    private String interfaceTag;
    private Player player;
    private int pageNumber = 0;
    private ShopFilter filter = ShopFilter.NONE;
    private InterfaceBuilder interfaceBuilder;

    public PlayerShop(Player player) {
        this.player = player;
        this.interfaceTag = ShopPage.LIST.toString();
        interfaceBuilder = new InterfaceBuilder(54, shopName + interfaceTag);
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
        interfaceBuilder.open(this.player);
        openShop.add(this);

    }

    public static PlayerShop getShopOf(String name) {
        for (PlayerShop shops : openShop)
            if (shops.player.getName().equals(name))
                return shops;
        return null;
    }

    public String getShopName() {
        return shopName;
    }

    public void nextPage() {
        if (pageNumber < Histeria.shop.getMaxPageNumber(filter)) {
            pageNumber++;
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
        }
    }

    public void previousPage() {
        if (pageNumber - 1 >= 0) {
            pageNumber--;
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
        }
    }

    public void openItemBuyPage(ItemStack item) {
        this.interfaceTag = ShopPage.SELL.toString();
        interfaceBuilder = new InterfaceBuilder(45, shopName + interfaceTag);
        interfaceBuilder.open(player);
        interfaceBuilder.setContent(Histeria.shop.getItemPage(Histeria.shop.getItemShop(item)));
    }

    public void destroy() {
        openShop.remove(this);
    }

    public void refresh() {
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
    }

    public void openListShop(){
        this.interfaceTag = ShopPage.LIST.toString();
        interfaceBuilder = new InterfaceBuilder(54, shopName + interfaceTag);
        interfaceBuilder.open(player);
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter));
    }

    public void setFilter(ShopFilter filter) {
        this.filter = filter;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
