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

        ItemStack[] pageContent = new ItemStack[25];

        // Creating list for pepare display (page getter content)
        int count = 0;
        for (int x = (24 * page); x <= (24 * (page + 1)); x++) {

            if (x < items.size())
                pageContent[count] = items.get(x).getMarketItem();
            else
                pageContent[count] = new ItemStack(Material.AIR);

            count++;
        }

        // Setting on the interface the content get by the page
        count = 0;
        for (int a = 0; a < 4; a++) {
            for (int id = 11; id <= 16; id++) {
                content[(id + a * 9)] = pageContent[count];
                count++;
            }
        }

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
                        result.getBytes("data")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MarketItem> getItems() {
        return items;
    }
}
