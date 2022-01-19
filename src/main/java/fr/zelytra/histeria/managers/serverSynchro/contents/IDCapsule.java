package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;

import java.io.IOException;
import java.io.InputStream;

public class IDCapsule implements Capsule {

    private byte[] message;

    public IDCapsule(Request request) {
        this.message = new byte[]{request.id};
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
        return 0;
    }

    @Override
    public PlayerData uncaps(PlayerData data, byte[] message, InputStream stream) throws IOException {
        return null;
    }
}
