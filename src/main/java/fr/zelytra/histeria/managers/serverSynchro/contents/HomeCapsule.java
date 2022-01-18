/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.home.Home;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.serverSynchro.PlayerData;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class HomeCapsule implements Capsule {

    private byte[] message;

    public HomeCapsule(CustomPlayer player) {
        this.message = new byte[]{};

        if (player == null) return;

        Home selectedHome = null;
        for (Home h : player.getHomes())
            if (h.hasTpServerRequest())
                selectedHome = h;


        if (selectedHome == null) return;

        // Collect location coordinates
        byte[] homeX = ByteBuffer.allocate(4).putInt((int) selectedHome.getLocation().getX()).array();
        byte[] homeY = ByteBuffer.allocate(4).putInt((int) selectedHome.getLocation().getY()).array();
        byte[] homeZ = ByteBuffer.allocate(4).putInt((int) selectedHome.getLocation().getZ()).array();

        // Collect worldName
        byte[] homeWorld;
        if (selectedHome.getLocation().getWorld() == null)
            homeWorld = selectedHome.getWorldName().getBytes(StandardCharsets.UTF_8);
        else
            homeWorld = selectedHome.getLocation().getWorld().getName().getBytes(StandardCharsets.UTF_8);
        byte[] homeWorldSize = ByteBuffer.allocate(4).putInt(homeWorld.length).array();

        message = ArrayUtils.addAll(homeX, homeY);
        message = ArrayUtils.addAll(message, homeZ);
        message = ArrayUtils.addAll(message, homeWorldSize);
        message = ArrayUtils.addAll(message, homeWorld);
    }


    @Override
    public byte[] build() {
        return message;
    }

    @Override
    public int getSize() {
        return message.length;
    }

    public PlayerData uncaps(PlayerData data, byte[] message) throws IOException {
        if (message.length<=0) return data;
        InputStream stream = new ByteArrayInputStream(message);

        // Reading coordinates
        byte[] buffer = new byte[4];
        stream.read(buffer);
        int x = ByteBuffer.wrap(buffer).getInt();
        stream.read(buffer);
        int y = ByteBuffer.wrap(buffer).getInt();
        stream.read(buffer);
        int z = ByteBuffer.wrap(buffer).getInt();

        // Reading worldName
        stream.read(buffer);
        buffer = new byte[ByteBuffer.wrap(buffer).getInt()];
        String worldName = new String(buffer, StandardCharsets.UTF_8);

        data.setTeleportTask(new Location(Bukkit.getWorld(worldName),x,y,z));

        return data;
    }
}
