/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;

public class CLocation implements Serializable {

    private final String world;
    private final double x, y, z;
    private final float pitch, yaw;

    public CLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld().getName();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public Location getCenteredLocation() {
        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        return location;

    }

}
