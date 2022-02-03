/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.blackMarket.builder.MarketItem;
import fr.zelytra.histeria.managers.market.blackMarket.builder.MarketPage;
import fr.zelytra.histeria.managers.market.blackMarket.builder.PlayerMarket;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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
                            openBuyPageTask(playerMarket, e.getCurrentItem(), (Player) e.getWhoClicked());
                        break;
                    }
                    case SPRUCE_SIGN -> {
                    }

                    default -> {
                        openBuyPageTask(playerMarket, e.getCurrentItem(), (Player) e.getWhoClicked());
                    }

                }
            }
        } else if (e.getView().getTitle().equals(PlayerMarket.interfaceTag + MarketPage.SELL)) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                switch (e.getCurrentItem().getType()) {

                    case BARRIER -> playerMarket.openMarketList();

                    case SLIME_BALL -> {
                        MarketItem marketItem = playerMarket.getMarket().getMarketItem(e.getInventory().getItem(22).getItemMeta().getPersistentDataContainer().get(MarketItem.key, PersistentDataType.INTEGER));
                        if (playerMarket.buyItem(marketItem))
                            e.getWhoClicked().closeInventory();
                    }

                    case CHEST -> {
                        //TODO Refound task
                    }

                    default -> {
                    }

                }
            }
        }
    }

    private void openBuyPageTask(PlayerMarket playerMarket, ItemStack item, Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            playerMarket.getMarket().refreshList();

            MarketItem marketItem = playerMarket.getMarket().getMarketItem(item.getItemMeta().getPersistentDataContainer().get(MarketItem.key, PersistentDataType.INTEGER));

            if (marketItem == null) {
                LangMessage.sendMessage(player, "blackmarket.itemNotExist");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return;
            }

            if (marketItem.hasViewer()) {
                LangMessage.sendMessage(player, "blackmarket.hasViewer");
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return;
            }
            marketItem.setViewerState(true);
            Bukkit.getScheduler().runTask(Histeria.getInstance(), () ->
                    playerMarket.openBuyPage(marketItem)
            );
        });
    }

    @EventHandler
    public void interfaceClose(InventoryCloseEvent e) {

        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;

        PlayerMarket playerMarket = PlayerMarket.getMarketOf(e.getPlayer().getName());
        if (playerMarket == null) return;

        // Resetting the state of the item
        if (e.getView().getTitle().equals(PlayerMarket.interfaceTag + MarketPage.SELL)) {
            MarketItem marketItem = playerMarket.getMarket().getMarketItem(e.getInventory().getItem(22).getItemMeta().getPersistentDataContainer().get(MarketItem.key, PersistentDataType.INTEGER));
            marketItem.setViewerState(false);
            if (e.getReason() != InventoryCloseEvent.Reason.OPEN_NEW)
                Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () ->
                        playerMarket.kill()
                );
        }

        // Killing the player market instance
        if (e.getView().getTitle().equals(PlayerMarket.interfaceTag + MarketPage.STAND) && e.getReason() != InventoryCloseEvent.Reason.OPEN_NEW) {
            playerMarket.kill();
        }
    }
}
