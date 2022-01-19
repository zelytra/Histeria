package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.commands.vanish.Vanish;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;

public class VanishCapsule implements Capsule {

    private byte[] message;
    public static final int length = 1;

    public VanishCapsule(Player player) {
        this.message = Vanish.isVanished(player) ? new byte[]{1} : new byte[]{0};
    }

    public VanishCapsule() {
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
        data.setVanish(message[0] == 1);
        return data;
    }
}
