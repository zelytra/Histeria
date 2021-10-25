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

    DEFAULT("Histerien"),
    VOTE("vote"),
    LORD("Lord"),
    MONARCH("Monarch"),
    DEMIGOD("DemiGod");

    private final String groupName;

    KitEnum(String group){
        this.groupName= group;
    }

    public String getGroupName() {
        return groupName;
    }
}
