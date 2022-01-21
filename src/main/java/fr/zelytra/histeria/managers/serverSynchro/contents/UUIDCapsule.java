package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class UUIDCapsule implements Capsule {

    private byte[] message;

    public UUIDCapsule(Player player) {
        byte[] uuidContent = player.getUniqueId().toString().getBytes(StandardCharsets.UTF_8);
        byte[] uuidLength = new byte[]{(byte) (player.getUniqueId().toString().length() & 0xff)};
        this.message = ArrayUtils.addAll(uuidLength, uuidContent);
    }

    public UUIDCapsule(String uuid) {
        byte[] uuidContent = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] uuidLength = new byte[]{(byte) (uuid.length() & 0xff)};
        this.message = ArrayUtils.addAll(uuidLength, uuidContent);
    }

    public UUIDCapsule() {
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
    public PlayerData uncaps(PlayerData data, byte[] message, InputStream input) throws IOException {
        return null;
    }
}
