/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;

public enum Message {
    PLAYER_PREFIX("§9[§bHisteria§9]§r "),
    CONSOLE_PREFIX("§9[§bHisteria§9]§r "),
    HISTALERT("§9[§bHist§3Alert§9]§r "),
    HISTLOGGER("§9[§bHist§3Logger§9]§r "),
    JOB("§8<§6Job§8>§r "),
    HISTBAN("§4[§cHistBan§4]§r ");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static String getHelp(String command) {
        return "§9[§bHisteria§9]§r §cWrong command syntax. Please refer to /" + command + " help.";
    }

    public static void startUpMessage() {
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "  _   _ _     _            _       ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + " | | | (_)___| |_ ___ _ __(_) __ _ ");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + " | |_| | / __| __/ _ \\ '__| |/ _` |");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + " |  _  | \\__ \\ ||  __/ |  | | (_| |");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + " |_| |_|_|___/\\__\\___|_|  |_|\\__,_|");
        Bukkit.getConsoleSender().sendMessage("§8|   §b" + "                                   ");
        Bukkit.getConsoleSender().sendMessage("§8|   §bby Zelytra");
        Bukkit.getConsoleSender().sendMessage("§8|   " + Histeria.version);
        Bukkit.getConsoleSender().sendMessage("§8|   ");

    }


}
