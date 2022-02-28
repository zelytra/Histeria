/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.utils.Utils;

public enum HomeLimit {

    DEFAULT(new String[]{"default", "vote"}, 2),
    LORD(new String[]{"lord"}, 4),
    MONARCH(new String[]{"monarch","guide"}, 6),
    DEMIGOD(new String[]{"demigod"}, 8),
    ADMIN(new String[]{}, 50);

    private String[] groups;
    private int homeLimit;

    HomeLimit(String[] groups, int limit) {
        this.groups = groups;
        this.homeLimit = limit;
    }

    public static HomeLimit getHomeLimit(String group) {
        if (Utils.canByPass(group))
            return ADMIN;
        else //Ugly...
            for (HomeLimit homeLimit : HomeLimit.values())
                for (String g : homeLimit.groups)
                    if (g.equalsIgnoreCase(group))
                        return homeLimit;

        return null;
    }

    public int getHomeLimit() {
        return homeLimit;
    }
}
