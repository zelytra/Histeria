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

import java.util.List;

public interface JobInterface {

    JobType getJob();

    int getLevel();

    double getXP();

    VisualItemStack getItemMenu();

    void executeReward(CustomPlayer player);

    List<String> getReward(int level);

}
