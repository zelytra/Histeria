package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;

import java.io.IOException;
import java.io.InputStream;

public class EmptyCapsule implements Capsule {
    private final byte[] message;

    public EmptyCapsule() {
        this.message = new byte[1];
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
        return message.length;
    }

    @Override
    public PlayerData uncaps(PlayerData data, byte[] message, InputStream stream) throws IOException {
        return null;
    }
}
