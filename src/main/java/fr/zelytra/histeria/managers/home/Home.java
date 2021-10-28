/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.home;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Location;

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

    public boolean hasTpServerRequest() {
        return tpServerRequest;
    }
}
