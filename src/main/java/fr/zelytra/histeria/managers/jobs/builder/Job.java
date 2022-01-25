/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.builder;

public interface Job {

    JobType getJob();

    int getLevel();

    double getXp();

    String getProgression();

    boolean consumeXP(double xp);


}
