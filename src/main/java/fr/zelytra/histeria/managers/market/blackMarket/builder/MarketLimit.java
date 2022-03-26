/*
 * Copyright (c) 2022. 
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *   
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.blackMarket.builder;

import fr.zelytra.histeria.utils.Utils;

public enum MarketLimit {
    DEFAULT(new String[]{"default", "vote"}, 2),
    LORD(new String[]{"lord"}, 4),
    MONARCH(new String[]{"monarch","guide"}, 6),
    DEMIGOD(new String[]{"demigod"}, 8),
    ADMIN(new String[]{}, 50);

    private String[] groups;
    private int marketLimit;

    MarketLimit(String[] groups, int limit) {
        this.groups = groups;
        this.marketLimit = limit;
    }

    public static MarketLimit getMarketLimit(String group) {
        if (Utils.canByPass(group))
            return ADMIN;
        else //Ugly...
            for (MarketLimit marketLimit : MarketLimit.values())
                for (String g : marketLimit.groups)
                    if (g.equalsIgnoreCase(group))
                        return marketLimit;

        return null;
    }

    public int getMarketLimit() {
        return marketLimit;
    }
}
