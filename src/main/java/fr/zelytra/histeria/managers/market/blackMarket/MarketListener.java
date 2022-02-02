/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MarketListener implements Listener {

    @EventHandler
    public void interfaceClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;

        PlayerMarket playerMarket = PlayerMarket.getMarketOf(e.getWhoClicked().getName());
        if (playerMarket == null) return;

        if (e.getView().getTitle().equals(PlayerMarket.interfaceTag + MarketPage.STAND)) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                switch (e.getCurrentItem().getType()) {

                    case BAKED_POTATO -> playerMarket.previousPage();

                    case POTATO -> {
                        if (CustomItemStack.hasTag(e.getCurrentItem()))
                            playerMarket.nextPage();
                        else
                            //playerShop.openItemBuyPage(e.getCurrentItem());
                            break;
                    }

                }
            }
        }
    }
}
