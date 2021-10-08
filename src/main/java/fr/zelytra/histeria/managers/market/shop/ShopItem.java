package fr.zelytra.histeria.managers.market.shop;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopItem {

    private ItemStack item;

    private ShopFilter filter;

    private int sellPrice;
    private int buyPrice;
    private int id;

    private String displayName;

    public ShopItem(int id, String itemName, String displayName, int sellPrice, int buyPrice, String filterName) {
        this.id = id;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.displayName = displayName;

        this.filter = ShopFilter.getByName(filterName);
        itemBuilder(itemName, displayName);
    }

    private void itemBuilder(String itemName, String displayName) {
        CustomMaterial customMaterial = CustomItemStack.getCustomMaterial(itemName);
        if (customMaterial != null)
            this.item = new CustomItemStack(customMaterial, 1).getItem();
        else
            this.item = new ItemStack(Material.getMaterial(itemName.toUpperCase(Locale.ROOT)));

        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(loreBuilder());
        this.item.setItemMeta(meta);

    }

    private List<String> loreBuilder() {
        List<String> lore = new ArrayList<>();
        lore.add("§6=====§b[§6Data§b]§6=====");
        lore.add("");
        lore.add("§bSell price -> §6" + sellPrice);
        lore.add("");
        lore.add("§bBuy price -> §6" + buyPrice);

        return lore;
    }


    public String toString() {
        return "Id: " + this.id + ",DisplayName: " + this.displayName + ",ItemName: " + PlainComponentSerializer.plain().serialize(this.item.displayName())
                + ",Filter: " + this.filter + ",SellPrice: " + this.sellPrice + ",BuyPrice: " + this.buyPrice;
    }

    public ItemStack getItem() {
        return item;
    }

    public ShopFilter getFilter() {
        return filter;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
