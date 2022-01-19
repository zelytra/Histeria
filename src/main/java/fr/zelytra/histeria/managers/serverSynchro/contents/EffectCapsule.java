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
import fr.zelytra.histeria.managers.serverSynchro.builder.ByteConverter;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class EffectCapsule implements Capsule {

    private byte[] message;
    public static final int length = 4;

    public EffectCapsule(Player player) {
        this.message = ByteConverter.potionArrayToBase64(player.getActivePotionEffects());
    }

    public EffectCapsule() {

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
        int effectLength = ByteBuffer.wrap(message).getInt();

        byte[] content = new byte[effectLength];
        input.read(content);

        data.setEffects(ByteConverter.potionArrayFromBase64(content));
        return data;
    }
}
