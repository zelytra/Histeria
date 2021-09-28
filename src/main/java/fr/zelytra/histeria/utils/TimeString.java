/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils;

public abstract class TimeString {

    public static int getTime(String arg) {

        int val = Integer.parseInt(arg.substring(0, arg.length() - 1));

        switch (arg.charAt(arg.length() - 1)) {
            case 'S':
            case 's':

                return val;
            case 'M':
            case 'm':

                return val * 60;

            case 'H':
            case 'h':

                return val * 3600;

            case 'D':
            case 'd':

                return val * 3600 * 24;

        }
        return 0;
    }
}
