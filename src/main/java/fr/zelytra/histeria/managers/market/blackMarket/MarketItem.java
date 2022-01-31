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
import fr.zelytra.histeria.managers.serverSynchro.builder.ByteConverter;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarketItem {

    private final int price;
    private final String seller;
    private final byte[] itemData;

    public MarketItem(String seller, int price, byte[] data) {

        this.seller = seller;
        this.price = price;
        this.itemData = data;

    }

    public MarketItem(String seller, int price, ItemStack item) {

        this.seller = seller;
        this.price = price;
        this.itemData = ByteConverter.itemStackToBase64(item);

    }

    public void publish() {
        try {
            MySQL mySQL = Histeria.mySQL;

            PreparedStatement statement = mySQL.getConnection().prepareStatement("INSERT INTO `BlackMarket` (`seller`,`price`,`data`) VALUES('" + seller + "', " + price + ", ?); ");
            statement.setBytes(1, itemData);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public byte[] getItemData() {
        return itemData;
    }

    public ItemStack getMarketItem() {
        //TODO construct the lore of the item for display seller information
        try {
            return ByteConverter.itemStackFromBase64(itemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
