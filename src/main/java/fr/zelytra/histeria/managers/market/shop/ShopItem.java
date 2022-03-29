package fr.zelytra.histeria.managers.market.shop;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Utils;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopItem {

    private ItemStack item;

    private final ShopFilter filter;

    private final int sellPrice;
    private final int buyPrice;
    private final int id;

    private final String displayName;
    private final String itemName;

    public ShopItem(int id, String itemName, String displayName, int sellPrice, int buyPrice, String filterName) {
        this.id = id;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.displayName = displayName;
        this.itemName = itemName;

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
        lore.add("");
        lore.add("§bBuy for: §6" + Utils.formatBigNumber(buyPrice) + "§f" + Emote.GOLD);
        lore.add("");
        lore.add("§bSell for: §6" + Utils.formatBigNumber(sellPrice) + "§f" + Emote.GOLD);

        return lore;
    }

    public ItemStack buildSellItem(int amount) {
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§bPrice -> §6" + Utils.formatBigNumber(buyPrice * amount) + "§f" + Emote.GOLD);
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(amount);
        return item;

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

    public ItemStack getFinalItemStack(int amount) {
        CustomMaterial customMaterial = CustomItemStack.getCustomMaterial(itemName);

        if (customMaterial != null)
            return new CustomItemStack(customMaterial, amount).getItem();
        else
            return new ItemStack(Material.getMaterial(itemName.toUpperCase(Locale.ROOT)),amount);

    }
}
