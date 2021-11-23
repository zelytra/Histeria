/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.server;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private String serverName = "§cNA";
    private List<String> serversList = new ArrayList<>();
    private int playerCount = 0;

    public Server() {
        new PMessage(SubChannel.SERVER_NAME, null, null);
        new PMessage(SubChannel.PLAYER_COUNT, null, new String[]{"ALL"});
        new PMessage(SubChannel.SERVERS_NAME, null, null);
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setServersList(List<String> serversList) {
        this.serversList = serversList;
    }

    public String getServerName() {
        return serverName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public List<String> getServersList() {
        return serversList;
    }

}
