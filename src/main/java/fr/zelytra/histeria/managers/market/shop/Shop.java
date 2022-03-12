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
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<ShopItem> shopItemList;

    private List<ShopItem> filterBlocks = new ArrayList<>();
    private List<ShopItem> filterItems = new ArrayList<>();
    private List<ShopItem> filterOres = new ArrayList<>();
    private List<ShopItem> filterCustom = new ArrayList<>();


    public Shop() {
        Histeria.log("Start loading shop...", LogType.INFO);
        loadShopData();
    }

    public void reload() {
        loadShopData();
    }

    private void loadShopData() {
        MySQL mySQL = Histeria.mySQL;

        shopItemList = new ArrayList<>();
        filterBlocks = new ArrayList<>();
        filterItems = new ArrayList<>();
        filterOres = new ArrayList<>();
        filterCustom = new ArrayList<>();

        try {
            ResultSet resultSet = mySQL.query("SELECT * FROM `Shop`");

            while (resultSet.next()) {
                ShopItem shopItem = new ShopItem(resultSet.getInt("id"),
                        resultSet.getString("itemName"),
                        resultSet.getString("name"),
                        resultSet.getInt("sellPrice"),
                        resultSet.getInt("buyPrice"),
                        resultSet.getString("type"));
                this.shopItemList.add(shopItem);

            }
            updateFilter();
            Histeria.log("Shop successfully loaded with " + shopItemList.size() + " items", LogType.INFO);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void updateFilter() {
        for (ShopItem item : shopItemList)
            switch (item.getFilter()) {
                case ORE:
                    filterOres.add(item);
                    break;
                case ITEM:
                    filterItems.add(item);
                    break;
                case BLOCK:
                    filterBlocks.add(item);
                    break;
                case CUSTOM_ITEM:
                    filterCustom.add(item);
                    break;
            }
    }

    public ItemStack[] getPage(int pageNumber, ShopFilter filter, PlayerShop playerShop) {
        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x <= 3; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[4] = playerShop.getHead();

        for (int x = 5; x <= 8; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[9] = new VisualItemStack(ShopFilter.BLOCK.getMaterial(), "§6BLOCK", filter.getMaterial() == ShopFilter.BLOCK.getMaterial(), "§6Filter by blocks").getItem();
        content[18] = new VisualItemStack(ShopFilter.ITEM.getMaterial(), "§6ITEMS", filter.getMaterial() == ShopFilter.ITEM.getMaterial(), "§6Filter by items").getItem();
        content[27] = new VisualItemStack(ShopFilter.ORE.getMaterial(), "§6ORE", filter.getMaterial() == ShopFilter.ORE.getMaterial(), "§6Filter by ores").getItem();
        content[36] = new VisualItemStack(ShopFilter.CUSTOM_ITEM.getMaterial(), "§6CUSTOM ITEMS", filter.getMaterial() == ShopFilter.CUSTOM_ITEM.getMaterial(), "§6Filter by custom items").getItem();

        for (int x = 45; x <= 53; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[53] = new VisualItemStack(Material.BARRIER, "§cBack", false, "§6Come back to previous page").getItem();
        content[48] = VisualType.PREVIOUS_ARROW.getItem();
        content[49] = new VisualItemStack(Material.SPRUCE_SIGN, "§6Page : §b" + pageNumber, false, "§6Filter : §b" + filter.name()).getItem();
        content[50] = VisualType.NEXT_ARROW.getItem();

        content[10] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[19] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[28] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[37] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[26] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[35] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[44] = VisualType.BLANK_BLUE_GLASSE.getItem();

        ItemStack[] itemContent = new ItemStack[25];

        // Creating list for pepare display (page getter content)
        int count = 0;
        for (int x = (24 * pageNumber); x <= (24 * (pageNumber + 1)); x++) {
            List<ShopItem> displayItems = getItemListByFilter(filter);

            if (x < displayItems.size())
                itemContent[count] = displayItems.get(x).getItem();
            else
                itemContent[count] = new ItemStack(Material.AIR);

            count++;
        }

        // Setting on the interface the content get by the page
        count = 0;
        for (int a = 0; a < 4; a++) {
            for (int id = 11; id <= 16; id++) {
                content[(id + a * 9)] = itemContent[count];
                count++;
            }
        }

        return content;
    }

    public ItemStack[] getItemPage(ShopItem item, PlayerShop playerShop) {
        ItemStack[] content = new ItemStack[45];

        for (int x = 0; x <= 8; x++) {
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();
        }
        for (int x = 36; x <= 44; x++) {
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();
        }
        content[4] = playerShop.getHead();
        content[40] = new VisualItemStack(Material.BARRIER, "§cBack", false, "§6Come back to previous page").getItem();

        content[9] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[18] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[27] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[26] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[35] = VisualType.BLANK_BLUE_GLASSE.getItem();


        if (item.getItem().getMaxStackSize() == 1) {
            content[22] = item.buildSellItem(1);
        } else {

            content[20] = item.buildSellItem(1);
            content[21] = item.buildSellItem(8);
            content[22] = item.buildSellItem(16);
            content[23] = item.buildSellItem(32);
            content[24] = item.buildSellItem(64);

        }

        return content;
    }

    public ItemStack[] getMenuPage(PlayerShop playerShop) {
        ItemStack[] content = new ItemStack[27];
        for (int x = 0; x <= 3; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[4] = playerShop.getHead();
        for (int x = 5; x <= 8; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[11] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[12] = VisualType.BUY.getItem();
        content[14] = VisualType.SELL.getItem();
        content[15] = VisualType.BLANK_BLUE_GLASSE.getItem();

        for (int x = 18; x <= 26; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        return content;
    }

    public ItemStack[] getSellPage(PlayerShop playerShop) {
        ItemStack[] content = new ItemStack[54];
        for (int x = 0; x <= 8; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[4] = playerShop.getHead();
        for (int x = 45; x <= 53; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[9] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[18] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[27] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[36] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[26] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[35] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[44] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[48] = new VisualItemStack(Material.BARRIER, "§cBack", false, "§6Come back to previous page").getItem();
        content[49] = new VisualItemStack(Material.SPRUCE_SIGN, "§6Total : §b" + 0, false).getItem();
        content[50] = VisualType.VALIDAY.getItem();

        return content;
    }

    private List<ShopItem> getItemListByFilter(ShopFilter filter) {
        switch (filter) {
            case ORE:
                return filterOres;

            case ITEM:
                return filterItems;

            case BLOCK:
                return filterBlocks;

            case CUSTOM_ITEM:
                return filterCustom;

            case NONE:
                return shopItemList;
        }
        return null;
    }

    public int getMaxPageNumber(ShopFilter filter) {
        switch (filter) {
            case ORE:
                return filterOres.size() / 24;

            case ITEM:
                return filterItems.size() / 24;

            case BLOCK:
                return filterBlocks.size() / 24;

            case CUSTOM_ITEM:
                return filterCustom.size() / 24;

            case NONE:
                return shopItemList.size() / 24;
        }
        return 0;
    }

    public ShopItem getItemShop(ItemStack item) {
        CustomMaterial material = CustomItemStack.getCustomMaterial(item);
        if (material != null || CustomMaterial.getCustomBlocks().contains(item.getType())) {
            for (ShopItem items : Histeria.shop.shopItemList)
                if (material == CustomItemStack.getCustomMaterial(items.getItem()))
                    return items;
        } else {
            for (ShopItem items : Histeria.shop.shopItemList)
                if (items.getItem().getType() == item.getType() && items.getFilter() != ShopFilter.CUSTOM_ITEM)
                    return items;

        }
        return null;
    }


}
