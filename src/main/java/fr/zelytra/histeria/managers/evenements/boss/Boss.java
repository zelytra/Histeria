/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.boss;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface Boss {

    void death(Location location);

    void drawCustomAttack();

    LivingEntity getEntity();

    double getMaxHealth();

}
