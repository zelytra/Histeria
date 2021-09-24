/*
 * Copyright (c) 2021. Zelytra
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.discord;

public enum WebHookType {
    BAN("https://raw.githubusercontent.com/zelytra/Histeria/master/img/histerite_core.png", "HistBan", "discord.ban"),
    COMMANDS("https://raw.githubusercontent.com/zelytra/Histeria/master/img/histerite_core.png", "HistCommands", "discord.commands"),
    ERROR("https://raw.githubusercontent.com/zelytra/Histeria/master/img/histerite_core.png","ErrorAlert","discord.error"),
    CHAT("https://raw.githubusercontent.com/zelytra/Histeria/master/img/histerite_core.png", "HistChat", "discord.chat"),
    ORE("https://raw.githubusercontent.com/zelytra/Histeria/master/img/histerite_core.png", "HistOre", "discord.ore");

    private final String avatar;
    private final String userName;
    private final String configTag;
    private final WebHookConfig webHookConfig;

    WebHookType(String avatar, String userName, String configTag) {
        this.avatar = avatar;
        this.userName = userName;
        this.configTag = configTag;
        this.webHookConfig = new WebHookConfig();
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getURL() {
        return this.webHookConfig.urlGetter.getString(this.configTag);
    }


}
