/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket;

public enum MarketPage {
    STAND("List"),
    SELL("Buy confirmation");

    private String tag;

    MarketPage(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
