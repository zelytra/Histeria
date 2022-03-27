/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.ByteConverter;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ItemSlotCapsule implements Capsule {

    private byte[] message;
    public static final int length = 4;

    public ItemSlotCapsule(Player player){
        byte[] inventoryContent = ByteConverter.itemStackArrayToBase64(player.getItemOnCursor().getContents());
        byte[] inventoryLength = ByteBuffer.allocate(4).putInt(inventoryContent.length).array();
        this.message = ArrayUtils.addAll(inventoryLength, inventoryContent);
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
    public PlayerData uncaps(PlayerData data, byte[] message, InputStream stream) throws IOException {
        return null;
    }
}
