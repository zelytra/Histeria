package fr.zelytra.histeria.managers.logs;

public enum LogType {
    INFO("\u001B[36m","INFO"),
    WARN("\u001B[33m","WARN"),
    ERROR("\u001B[31m","ERROR");

    String consoleColor;
    String name;

    LogType(String consoleColor, String name) {
        this.consoleColor = consoleColor;
        this.name = name;
    }

    public String getConsoleColor() {
        return consoleColor;
    }

    public String getName() {
        return name;
    }
}
