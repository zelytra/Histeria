/*
 * Copyright (c) 2021-2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils.timer;

public abstract class TimeFormater {

    public static String display(long timeInMilli) {

        //Milliseconds display
        if (timeInMilli <= 1000) {
            return "§e" + (timeInMilli) + "§6ms";
        }
        //Seconds display
        else if (timeInMilli <= 60000) {
            int timeInSec = (int) (timeInMilli / 1000) % 60;
            return "§e" + timeInSec + "§6s";
        }
        //Minutes display
        else if (timeInMilli <= 3600000) {
            int remainingSec = (int) (timeInMilli / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            return "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        }
        //Hours display
        else {
            int remainingSec = (int) (timeInMilli / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            int TimeInHour = remainingSec / 3600;
            return "§e" + TimeInHour + "§6h" + "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        }
    }


    public static String display(int timeInSec) {

        //Seconds display
        if (timeInSec <= 60) {
            return "§e" + timeInSec + "§6s";
        }
        //Minutes display
        else if (timeInSec <= 3600) {
            int time = (timeInSec);
            int timeInSecs = (timeInSec % 60);
            int timeInMin = (time % 3600) / 60;
            return "§e" + timeInMin + "§6m" + "§e" + timeInSecs + "§6s";
        }

        //Hours display
        else {
            int time = (timeInSec);
            int timeInSecs = (timeInSec % 60);
            int timeInMin = (time % 3600) / 60;
            int TimeInHour = time / 3600;
            return "§e" + TimeInHour + "§6h" + "§e" + timeInMin + "§6m" + "§e" + timeInSecs + "§6s";
        }
    }

    public static String compareToNow(long timeInMillis) {
        //Milliseconds display
        if (System.currentTimeMillis() - timeInMillis <= 1000) {
            return "§e" + (System.currentTimeMillis() - timeInMillis) + "§6ms";
        }
        //Seconds display
        else if (System.currentTimeMillis() - timeInMillis <= 60000) {
            int timeInSec = (int) ((System.currentTimeMillis() - timeInMillis) / 1000) % 60;
            return "§e" + timeInSec + "§6s";
        }
        //Minutes display
        else if (System.currentTimeMillis() - timeInMillis <= 3600000) {
            int remainingSec = (int) ((System.currentTimeMillis() - timeInMillis) / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            return "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        }
        //Hours display
        else if (System.currentTimeMillis() - timeInMillis <= 86400000) {
            int remainingSec = (int) ((System.currentTimeMillis() - timeInMillis) / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            int TimeInHour = (remainingSec % 86400) / 3600;
            return "§e" + TimeInHour + "§6h" + "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        } else {
            return "§cNA";
        }
    }


}
