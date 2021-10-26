/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.server;

public enum SubChannel {
    CONNECT("Connect"),
    SERVER_NAME("GetServer"),
    SERVERS_NAME("GetServers"),
    PLAYER_COUNT("PlayerCount");

    private String channel;

    SubChannel(String channel) {
        this.channel = channel;
    }

    public static SubChannel get(String name) {
        for (SubChannel subChannel : SubChannel.values())
            if (subChannel.getName().equals(name))
                return subChannel;
        return null;
    }

    public String getName() {
        return channel;
    }
}
