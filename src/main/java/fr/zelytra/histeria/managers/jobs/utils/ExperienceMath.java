/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.utils;

public abstract class ExperienceMath {

    /**
     * All Experience calculation constant
     * Check this desmos graph for adjusting constant and create a good balenced system
     * https://www.desmos.com/calculator/7qbvhieqtb
     */
    private static int xp_0_to_1 = 100;
    private static int xp_99_to_100 = 170000;
    private static double a = Math.pow(xp_99_to_100 / xp_0_to_1, 1 / 99f);

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param xp Job total experience
     * @return the level
     */
    public static int getLevelFromXp(int xp) {
        return (int) Math.round(Math.log(1 - xp / xp_0_to_1 * (1 - a)) / Math.log(a));
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Job level
     * @return the amount of experience which a level can contain
     */
    public static int getXpFromLevel(int level) {
        return (int) (xp_0_to_1 * Math.pow(a, level - 1));
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Job level
     * @return the total amount of experience since input level
     */
    public static int getRecursiveXpFromLevel(int level) {
        if (level > 0)
            return (int) (xp_0_to_1 * (1 - Math.pow(a, level)) / (1 - a));

        return 0;
    }

    /**
     * Experience calculation system
     * by TimEpsilon
     *
     * @param level Current level object
     * @param xp    Job total experience
     * @return the quantity of experience remaining before job level up
     */
    public static int getXpRemaining(int level, int xp) {
        if (level < 100)
            return getRecursiveXpFromLevel(level + 1) - xp;
        return 0;
    }

}
