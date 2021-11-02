/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Home {

    private CustomPlayer customPlayer;
    private Location location;
    private String serverName;
    private String name;
    private boolean tpServerRequest = false;

    public Home(CustomPlayer customPlayer, Location location, String server, String homeName) {
        this.customPlayer = customPlayer;
        this.location = location;
        this.serverName = server;
        this.name = homeName;
    }

    public CustomPlayer getCustomPlayer() {
        return customPlayer;
    }

    public Location getLocation() {
        return location;
    }

    public String getServerName() {
        return serverName;
    }

    public String getName() {
        return name;
    }

    public void setServerRequest() {
        this.tpServerRequest = true;
    }

    public void teleport(Player player) {

        if (Histeria.server.getServerName().equalsIgnoreCase(this.getServerName())) {

            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "home.teleport", this.getName());
            player.teleport(this.getLocation());

        } else {

            this.setServerRequest();
            LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "home.serverTeleport", "");
            new SwitchServer(player).switchTo(this.getServerName());

        }
        Histeria.log("§6" + player.getName() + " §ehas been teleported to §6" + this, LogType.INFO);
        return;

    }

    public boolean hasTpServerRequest() {
        return tpServerRequest;
    }

    @Override
    public String toString() {
        return "Player: " + customPlayer.getName() + " | Name: " + name + "| Location: x:" + location.getBlockX() + " y:" + location.getBlockY() + " z:" + location.getBlockZ() + " | Server: " + serverName;
    }
}
