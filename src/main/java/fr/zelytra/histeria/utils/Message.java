/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils;

public enum Message {
    PLAYER_PREFIX("§9[§bHisteria§9]§r "),
    CONSOLE_PREFIX("§9[§bHisteria§9]§r "),
    HISTALERT("§9[§bHist§cAlert§9]§r "),
    HISTBAN("§4[§cHistBan§4]§r "),
    CONSOLE_STRATUP("\n" +
            "§6  _    _ _____  _____ _______ ______ _____  _____          \n" +
            "§6 | |  | |_   _|/ ____|__   __|  ____|  __ \\|_   _|   /\\    \n" +
            "§6 | |__| | | | | (___    | |  | |__  | |__) | | |    /  \\   \n" +
            "§6 |  __  | | |  \\___ \\   | |  |  __| |  _  /  | |   / /\\ \\  \n" +
            "§6 | |  | |_| |_ ____) |  | |  | |____| | \\ \\ _| |_ / ____ \\ \n" +
            "§6 |_|  |_|_____|_____/   |_|  |______|_|  \\_\\_____/_/    \\_\\\n" +
            "                                                           \n" +
            "                                                           \n");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

    public static String getHelp(String command) {
        return "§9[§bHisteria§9]§r §cWrong command syntax. Please refer to /" + command + " help.";
    }


}
