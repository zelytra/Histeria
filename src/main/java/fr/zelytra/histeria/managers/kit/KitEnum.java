/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.kit;

public enum KitEnum {

    DEFAULT("default", 24),
    VOTE("vote", 24),
    LORD("lord", 24),
    MONARCH("monarch", 24),
    DEMIGOD("demigod", 24);

    private final String groupName;
    private final int coolDown; // CD in hour

    KitEnum(String group, int coolDown) {
        this.groupName = group;
        this.coolDown = coolDown;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getCoolDown() {
        return coolDown * 3600;
    }
}
