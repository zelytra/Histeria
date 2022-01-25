/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.builder;

import fr.zelytra.histeria.managers.visual.chat.Emote;

public enum JobType {

    MINER(Emote.FIGHT),
    FARMER(Emote.FIGHT),
    ENCHANTER(Emote.FIGHT),
    FIGHTER(Emote.FIGHT);

    public final Emote badge;

    JobType(Emote badge) {
        this.badge = badge;
    }

}
