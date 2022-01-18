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

public class FoodCapsule implements Capsule {

    private final byte[] message;

    public FoodCapsule(Player player) {
        this.message = ByteBuffer.allocate(4).putInt(player.getFoodLevel()).array();
    }

    @Override
    public byte[] build() {
        return message;
    }

    @Override
    public int getSize() {
        return message.length;
    }
}
