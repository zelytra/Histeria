/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.builder;

import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.managers.player.CustomPlayer;

public interface JobInterface {

    JobType getJob();

    int getLevel();

    double getXP();

    VisualItemStack getItemMenu();

    void executeReward(CustomPlayer player);

    String getReward(int level);

}
