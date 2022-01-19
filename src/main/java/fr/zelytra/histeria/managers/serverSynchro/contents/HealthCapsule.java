/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class HealthCapsule implements Capsule {

    private byte[] message;
    public static final int length = 8;

    public HealthCapsule(Player player) {
        this.message = ByteBuffer.allocate(8).putDouble(player.getHealth()).array();
    }

    public HealthCapsule() {
    }

    @Override
    public byte[] build() {
        return message;
    }

    @Override
    public int getSize() {
        return message.length;
    }

    @Override
    public int firstPacketSize() {
        return length;
    }

    @Override
    public PlayerData uncaps(PlayerData data, byte[] message, InputStream input) throws IOException {
        data.setHealth(ByteBuffer.wrap(message).getDouble());
        return data;
    }
}
