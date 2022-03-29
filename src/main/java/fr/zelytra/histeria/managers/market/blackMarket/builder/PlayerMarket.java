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
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.blackMarket.Market;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerMarket {

    private static final List<PlayerMarket> openMarket = new ArrayList<>();
    public static final String interfaceTag = "§6BlackMarket | §e";

    private final CustomPlayer player;
    private final Market market;
    private InterfaceBuilder marketInterface;
    private int page = 0;

    public PlayerMarket(Player player) {
        this.player = CustomPlayer.getCustomPlayer(player.getName());
        this.market = new Market();
        openMarketList();
        openMarket.add(this);
    }

    public void nextPage() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            market.refreshList();
            if (page < market.getMaxPageNumber()) {
                page++;
                marketInterface.setContent(market.getListPage(page));
            }
        });
    }

    public void previousPage() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            market.refreshList();
            if (page - 1 >= 0) {
                page--;
                marketInterface.setContent(market.getListPage(page));
            }
        });
    }

    public void openMarketList() {
        this.marketInterface = new InterfaceBuilder(54, interfaceTag + MarketPage.STAND);
        this.marketInterface.open(player.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            market.refreshList();
            this.marketInterface.setContent(market.getListPage(page));
        });
    }

    public void openBuyPage(MarketItem marketItem) {

        this.marketInterface = new InterfaceBuilder(45, interfaceTag + MarketPage.SELL);
        this.marketInterface.open(player.getPlayer());
        this.marketInterface.setContent(market.getBuyPage(marketItem, marketItem.getSeller().equalsIgnoreCase(player.getName())));

    }

    public boolean buyItem(MarketItem item) {

        if (!player.getBankAccount().hasEnoughFor(item.getPrice())) {
            LangMessage.sendMessage(player.getPlayer(), "shop.notEnoughMoney");
            return false;
        }

        if (Utils.getEmptySlots(player.getPlayer()) < 2) {
            LangMessage.sendMessage(player.getPlayer(), "miscellaneous.notEnoughSpace");
            return false;
        }

        // Seller money giver
        CustomPlayer target;
        if (Bukkit.getPlayer(item.getSeller()) != null)
            target = CustomPlayer.getCustomPlayer(item.getSeller());
        else
            target = new CustomPlayer(item.getSeller());

        target.getBankAccount().addMoney(item.getPrice());
        player.getBankAccount().takeMoney(item.getPrice());
        player.getPlayer().getInventory().addItem(item.getRawItem());
        item.delete();
        LangMessage.sendMessage(player.getPlayer(), "blackmarket.purchaseSuccess");
        if (Bukkit.getPlayer(item.getSeller()) != null)
            LangMessage.sendMessage(target.getPlayer(), Message.PLAYER_PREFIX.getMsg(), "blackmarket.purchaseReceived", " §6" + Utils.formatBigNumber(item.getPrice()) + Emote.GOLD);
        else {
            target.saveData();
            target.destroy();
        }
        return true;

    }

    public Market getMarket() {
        return market;
    }

    public static PlayerMarket getMarketOf(String name) {
        for (PlayerMarket market : openMarket)
            if (market.player.getName().equalsIgnoreCase(name))
                return market;
        return null;
    }

    public void kill() {
        openMarket.remove(this);
    }

}
