/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.shop;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onInterfaceClick(InventoryClickEvent e) {
        PlayerShop playerShop = PlayerShop.getShopOf(e.getWhoClicked().getName());

        if (playerShop == null) return;

        if (e.getInventory().getHolder() instanceof CustomGUI) {
            if (e.getView().getTitle().equals(playerShop.getShopName() + ShopPage.LIST)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case BAKED_POTATO:
                            playerShop.previousPage();
                            break;
                        case POTATO:
                            playerShop.nextPage();
                            break;
                        case GRASS_BLOCK:
                            playerShop.setFilter(ShopFilter.BLOCK);
                            playerShop.setPageNumber(0);
                            playerShop.refresh();
                            break;
                        case DEAD_BUSH:
                            playerShop.setFilter(ShopFilter.ITEM);
                            playerShop.setPageNumber(0);
                            playerShop.refresh();
                            break;
                        case DIAMOND_ORE:
                            playerShop.setFilter(ShopFilter.ORE);
                            playerShop.setPageNumber(0);
                            playerShop.refresh();
                            break;
                        case INFESTED_COBBLESTONE:
                            playerShop.setFilter(ShopFilter.CUSTOM_ITEM);
                            playerShop.setPageNumber(0);
                            playerShop.refresh();
                            break;
                        case SPRUCE_SIGN:
                            playerShop.setFilter(ShopFilter.NONE);
                            playerShop.setPageNumber(0);
                            playerShop.refresh();
                            break;
                        case BARRIER:
                            playerShop.openMenuPage();
                            break;
                        default:
                            playerShop.openItemBuyPage(e.getCurrentItem());
                            break;

                    }
                }
            } else if (e.getView().getTitle().equals(playerShop.getShopName() + ShopPage.BUY)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case BARRIER:
                            playerShop.openListShop();
                            break;
                        default:
                            playerShop.buyItems(e.getCurrentItem());
                            break;


                    }
                }
            } else if (e.getView().getTitle().equals(playerShop.getShopName() + ShopPage.MENU)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case MUTTON:
                            playerShop.openListShop();
                            break;
                        case COOKED_MUTTON:
                            playerShop.openSellPage();
                            break;
                    }
                }
            } else if (e.getView().getTitle().equals(playerShop.getShopName() + ShopPage.SELL)) {
                if (e.getCurrentItem() != null) {
                    switch (e.getCurrentItem().getType()) {
                        case BARRIER:
                            e.setCancelled(true);
                            playerShop.openMenuPage();
                            break;

                    }
                }
            }
        }
    }

    @EventHandler
    public void onShopClose(InventoryCloseEvent e) {
        PlayerShop playerShop = PlayerShop.getShopOf(e.getPlayer().getName());

        if (playerShop == null) return;

        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().contains(playerShop.getShopName())) {
            //playerShop.destroy();

        }
    }
}
