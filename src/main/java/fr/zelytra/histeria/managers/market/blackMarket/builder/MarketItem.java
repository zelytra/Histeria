/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket.builder;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.serverSynchro.builder.ByteConverter;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarketItem {

    public static final NamespacedKey key = new NamespacedKey(Histeria.getInstance(), "blackMarkerId");

    private final int price;
    private final String seller;
    private final byte[] itemData;
    private int id;

    /**
     * @param seller Seller name
     * @param price  Price of the item
     * @param data   The raw data of the item
     */

    public MarketItem(String seller, int price, byte[] data, int id) {

        this.seller = seller;
        this.price = price;
        this.itemData = data;
        this.id = id;

    }

    /**
     * @param seller Seller name
     * @param price  Price of the item
     * @param item   ItemStack of the item to sell
     */
    public MarketItem(String seller, int price, ItemStack item) {

        this.seller = seller;
        this.price = price;
        this.itemData = ByteConverter.itemStackToBase64(item);

    }

    public boolean hasViewer() {
        try {
            MySQL mySQL = Histeria.mySQL;

            ResultSet result = mySQL.query("SELECT `viewer` FROM `BlackMarket` WHERE id = " + id + ";");
            if (result.next())
                return result.getInt("viewer") == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    public void delete() {

        MySQL mySQL = Histeria.mySQL;
        mySQL.update("DELETE FROM `BlackMarket` WHERE `id` = " + id + " AND `seller` = '" + seller + "' ;");

    }

    public void setViewerState(boolean viewerState) {
        MySQL mySQL = Histeria.mySQL;
        mySQL.update("UPDATE `BlackMarket` SET `viewer` = " + (viewerState ? 1 : 0) + " WHERE `id` = " + id + " ;");
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

    public int getId() {
        return id;
    }

    public ItemStack getMarketItem() {
        try {
            ItemStack item = ByteConverter.itemStackFromBase64(itemData);
            ItemMeta meta = item.getItemMeta();

            // Setting lore information
            List<String> lore = new ArrayList<>();
            if (meta.getLore() != null)
                lore = meta.getLore();
            lore.add("");
            lore.add("§8● §6Seller: §f" + seller);
            lore.add("§8● §6Price: §f" + Utils.formatBigNumber(price) + Emote.GOLD);
            lore.add("§8● §6ID: §f" + id);
            meta.setLore(lore);


            // Setting id in PDC
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
            item.setItemMeta(meta);

            return item;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemStack(Material.AIR);
    }

    public ItemStack getBuyItem() {
        try {
            ItemStack item = ByteConverter.itemStackFromBase64(itemData);
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
            item.setItemMeta(meta);
            return item;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemStack(Material.AIR);
    }

    public ItemStack getRawItem(){
        try {
            return ByteConverter.itemStackFromBase64(itemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemStack(Material.AIR);
    }

    public ItemStack getPaperBuyInfo() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eItem informations");
        // Setting lore information
        List<String> lore = new ArrayList<>();
        if (meta.getLore() != null)
            lore = meta.getLore();
        lore.add("§8● §6Seller: §f" + seller);
        lore.add("§8● §6Price: §f" + Utils.formatBigNumber(price) + Emote.GOLD);
        lore.add("§8● §6ID: §f" + id);
        meta.setLore(lore);

        // Setting id in PDC
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
        item.setItemMeta(meta);

        return item;
    }
}
