/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket;

import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerMarket {

    public static final List<PlayerMarket> openMarket = new ArrayList<>();
    private static final String interfaceTag = "§6BlackMarket | §e";

    private final CustomPlayer player;
    private final Market market;
    private InterfaceBuilder marketInterface;

    public PlayerMarket(Player player) {
        this.player = CustomPlayer.getCustomPlayer(player.getName());
        this.market = new Market();
        openMarketList();
        openMarket.add(this);
    }


    public void openMarketList() {
        this.marketInterface = new InterfaceBuilder(54, interfaceTag + MarketPage.STAND);
        this.marketInterface.open(player.getPlayer());
        this.marketInterface.setContent(market.getListPage(0));
    }


    public static PlayerMarket getMarketOf(String name) {
        for (PlayerMarket market : openMarket)
            if (market.player.getName().equalsIgnoreCase(name))
                return market;
        return null;
    }

}
