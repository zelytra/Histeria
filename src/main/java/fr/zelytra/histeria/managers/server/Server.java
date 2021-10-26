/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.server;

public class Server {

    private String serverName = "§cNA";
    private String[] serversList;
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

    public void setServersList(String[] serversList) {
        this.serversList = serversList;
    }

    public String getServerName() {
        return serverName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public String[] getServersList() {
        return serversList;
    }


}
