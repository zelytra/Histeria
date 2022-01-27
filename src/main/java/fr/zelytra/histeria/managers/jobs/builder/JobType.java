/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.builder;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.visual.chat.Emote;

public enum JobType {

    MINER(Emote.FIGHT, CustomMaterial.BADGE_MINER),
    FARMER(Emote.FIGHT, CustomMaterial.BADGE_FARMER),
    ENCHANTER(Emote.FIGHT, CustomMaterial.BADGE_ENCHANTER),
    FIGHTER(Emote.FIGHT, CustomMaterial.BADGE_FIGHTER);

    public final Emote badge;
    public final CustomMaterial item;

    JobType(Emote badge, CustomMaterial material) {
        this.badge = badge;
        this.item = material;
    }

    public static JobType get(CustomMaterial material) {
        for (JobType type : JobType.values())
            if (type.item == material)
                return type;
        return null;
    }
}
