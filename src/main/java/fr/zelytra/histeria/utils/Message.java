package fr.zelytra.histeria.utils;

import fr.zelytra.histeria.Histeria;

public class Message {
    public static String getPlayerPrefixe() {
        return "§9[§bHisteria§9]§r ";
    }

    public static String getConsolePrefixe() {
        return "§9[§bHisteria§9]§r ";
    }

    public static String getHelp(String command) {
        return "§9[§bHisteria§9]§r §cWrong command syntax. Please refer to /" + command + " help.";
    }

    public static void startup() {
        Histeria.getInstance().getServer().getConsoleSender().sendMessage("");
        Histeria.getInstance().getServer().getConsoleSender().sendMessage("§b               Histeria   V2.0§r");
        Histeria.getInstance().getServer().getConsoleSender().sendMessage("§b                  by Zelytra§r");
        Histeria.getInstance().getServer().getConsoleSender().sendMessage("");

    }
}
