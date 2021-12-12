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
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerShop implements Listener {
    private static List<PlayerShop> openShop = new ArrayList<>();

    private String shopName = "§6Shop : ";
    private String interfaceTag;
    private Player player;
    private CustomPlayer customPlayer;
    private int pageNumber = 0;
    private ShopFilter filter = ShopFilter.NONE;
    private InterfaceBuilder interfaceBuilder;
    private ShopItem shopItem;

    public PlayerShop(Player player) {
        this.player = player;
        this.customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        openMenuPage();

        PlayerShop toRemove = null;
        for (PlayerShop playerShop : openShop)
            if (playerShop.player.getName().equals(this.player.getName()))
                toRemove = playerShop;

        openShop.remove(toRemove);
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
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter, this));
        }
    }

    public void previousPage() {
        if (pageNumber - 1 >= 0) {
            pageNumber--;
            interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter, this));
        }
    }

    public void openItemBuyPage(ItemStack item) {
        this.interfaceTag = ShopPage.BUY.toString();
        interfaceBuilder = new InterfaceBuilder(45, shopName + interfaceTag);
        interfaceBuilder.open(player);

        this.shopItem = Histeria.shop.getItemShop(item);
        interfaceBuilder.setContent(Histeria.shop.getItemPage(shopItem, this));
    }

    public void openMenuPage() {
        this.interfaceTag = ShopPage.MENU.toString();
        interfaceBuilder = new InterfaceBuilder(27, shopName + interfaceTag);
        interfaceBuilder.open(player);
        interfaceBuilder.setContent(Histeria.shop.getMenuPage(this));
    }

    public void openSellPage() {
        this.interfaceTag = ShopPage.SELL.toString();
        interfaceBuilder = new InterfaceBuilder(54, shopName + interfaceTag);
        interfaceBuilder.open(player);
        interfaceBuilder.setContent(Histeria.shop.getSellPage(this));
    }

    public void destroy() {
        openShop.remove(this);
    }

    public void refresh() {
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter, this));
    }

    public void openListShop() {
        this.interfaceTag = ShopPage.LIST.toString();
        interfaceBuilder = new InterfaceBuilder(54, shopName + interfaceTag);
        interfaceBuilder.open(player);
        interfaceBuilder.setContent(Histeria.shop.getPage(pageNumber, filter, this));
    }

    public void refreshHead() {
        interfaceBuilder.getInventory().setItem(4, getHead());
    }

    public void refreshSellPage(int sellPrice) {
        interfaceBuilder.getInventory().setItem(49, new VisualItemStack(Material.SPRUCE_SIGN, "§6Total : §b" + sellPrice + " §f" + Emote.GOLD, false).getItem());
    }

    public int getSellPrice(List<ItemStack> content) {
        int finalPrice = 0;
        for (ItemStack item : content) {
            ShopItem shopItem = Histeria.shop.getItemShop(item);
            if (shopItem != null)
                finalPrice += shopItem.getSellPrice() * item.getAmount();
        }
        return finalPrice;

    }

    public void setFilter(ShopFilter filter) {
        this.filter = filter;
    }

    public ItemStack getHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) head.getItemMeta();

        skull.setDisplayName("§b" + player.getName());

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6" + customPlayer.getBankAccount().getMoney() + " §f" + Emote.GOLD);

        skull.setLore(lore);
        skull.setOwningPlayer(player);
        head.setItemMeta(skull);
        return head;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void buyItems(ItemStack currentItem) {
        if (this.shopItem != null) {
            int finalPrice = this.shopItem.getBuyPrice() * currentItem.getAmount();

            if (customPlayer.getBankAccount().getMoney() < finalPrice) {
                LangMessage.sendMessage(player, "shop.notEnoughMoney");
            } else if (Utils.isFullInv(customPlayer.getPlayer())) {
                LangMessage.sendMessage(player, "miscellaneous.notEnoughSpace");
            } else {
                customPlayer.getBankAccount().takeMoney(finalPrice);
                player.getInventory().addItem(shopItem.getFinalItemStack(currentItem.getAmount()));
                LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "shop.buyItem", "§6" + shopItem.getDisplayName() + " x" + currentItem.getAmount() + " §a-> §6" + finalPrice + " §f" + Emote.GOLD);
            }
        }
    }


    public void sellItems(Inventory inventory, int sellPrice) {
        customPlayer.getBankAccount().addMoney(sellPrice);
        for (int x = 0; x < 4; x++) {
            for (int id = 10; id <= 16; id++) {
                if (inventory.getContents()[id + x * 9] == null)
                    continue;
                else
                    inventory.setItem(id + x * 9, new ItemStack(Material.AIR));

            }
        }
        LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "shop.sellItems", "§6" + sellPrice + " §f" + Emote.GOLD);
    }
}
