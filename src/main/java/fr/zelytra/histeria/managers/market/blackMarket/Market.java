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
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.market.blackMarket.builder.MarketItem;
import fr.zelytra.histeria.managers.mysql.MySQL;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Market {

    private List<MarketItem> items = new ArrayList<>();

    public Market() {
        refreshList();
    }

    public ItemStack[] getListPage(int page) {
        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        content[48] = VisualType.PREVIOUS_ARROW.getItem();
        content[49] = new VisualItemStack(Material.SPRUCE_SIGN, "§7Page : §e" + page, false).getItem();
        content[50] = VisualType.NEXT_ARROW.getItem();

        ItemStack[] pageContent = new ItemStack[28];

        // Creating list for pepare display (page getter content)
        int count = 0;
        for (int x = (27 * page); x <= (27 * (page + 1)); x++) {

            if (x < items.size())
                pageContent[count] = items.get(x).getMarketItem();
            else
                pageContent[count] = new ItemStack(Material.AIR);

            count++;
        }

        // Setting on the interface the content get by the page
        count = 0;
        for (int a = 0; a < 4; a++) {
            for (int id = 10; id <= 16; id++) {
                content[(id + a * 9)] = pageContent[count];
                count++;
            }
        }

        return content;
    }

    public ItemStack[] getBuyPage(MarketItem item, boolean isSeller) {

        ItemStack[] content = new ItemStack[45];

        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        content[20] = item.getPaperBuyInfo();
        content[22] = item.getBuyItem();
        content[24] = new VisualItemStack(CustomMaterial.VALIDAY, "§aBuy", false, "Click to buy the item").getItem();
        content[40] = new VisualItemStack(Material.BARRIER, "§cBack to menu", false).getItem();

        if (isSeller)
            content[4] = new VisualItemStack(Material.CHEST, "§eClick here to get back your item", false, "§eThis action will remove the item from the market").getItem();


        return content;
    }


    public void refreshList() {

        items = new ArrayList<>();

        try {
            MySQL mySQL = Histeria.mySQL;
            ResultSet result = mySQL.query("SELECT * FROM `BlackMarket`");

            while (result.next()) {
                items.add(new MarketItem(
                        result.getString("seller"),
                        result.getInt("price"),
                        result.getBytes("data"),
                        result.getInt("id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MarketItem> getItems() {
        return items;
    }

    public MarketItem getMarketItem(int id) {
        for (MarketItem item : items)
            if (item.getId() == id)
                return item;
        return null;
    }

    public int getMaxPageNumber() {
        return items.size() / 28;
    }
}
