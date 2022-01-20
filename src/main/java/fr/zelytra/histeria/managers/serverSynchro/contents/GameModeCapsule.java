package fr.zelytra.histeria.managers.serverSynchro.contents;

import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;

public class GameModeCapsule implements Capsule {

    private byte[] message;
    public static final int length = 1;

    public GameModeCapsule(Player player) {
        switch (player.getGameMode()) {
            case CREATIVE:
                this.message = new byte[]{1};
                break;
            case ADVENTURE:
                this.message = new byte[]{2};
                break;
            case SPECTATOR:
                this.message = new byte[]{3};
                break;
            case SURVIVAL:
            default:
                this.message = new byte[]{0};
                break;
        }
    }

    public GameModeCapsule() {
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
        switch (message[0]) {
            case 1:
                data.setGameMode(GameMode.CREATIVE);
                break;
            case 2:
                data.setGameMode(GameMode.ADVENTURE);
                break;
            case 3:
                data.setGameMode(GameMode.SPECTATOR);
                break;
            case 0:
            default:
                data.setGameMode(GameMode.SURVIVAL);
                break;
        }
        return data;
    }
}
