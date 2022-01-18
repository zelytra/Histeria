/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;

public class XPCapsule implements Capsule {

    private final byte[] message;

    public XPCapsule(Player player) {
        this.message = ByteBuffer.allocate(4).putInt(player.getLevel()).array();
    }

    @Override
    public byte[] build() {
        return new byte[0];
    }

    @Override
    public int getSize() {
        return 0;
    }
}
