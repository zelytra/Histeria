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

public class InventoryCapsule implements Capsule {

    private byte[] message;
    public static final int length = 4;

    public InventoryCapsule(Player player) {
        byte[] inventoryContent = ByteConverter.itemStackArrayToBase64(player.getInventory().getContents());
        byte[] inventoryLength = ByteBuffer.allocate(4).putInt(inventoryContent.length).array();
        this.message = ArrayUtils.addAll(inventoryLength, inventoryContent);
    }

    public InventoryCapsule() {
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

        int invLength = ByteBuffer.wrap(message).getInt();

        byte[] content = new byte[invLength];
        input.read(content);

        data.setInventory(ByteConverter.itemStackArrayFromBase64(content));
        return data;

    }


}
