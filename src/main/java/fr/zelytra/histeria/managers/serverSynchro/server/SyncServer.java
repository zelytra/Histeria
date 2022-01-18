/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.server;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.serverSynchro.SynchroConfig;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SyncServer {

    private static int timeOut = 2;

    private final Request request;
    private final Player player;
    private final Socket server;

    private boolean isCommunicationOver = false;

    public SyncServer(Player player, Request request) {
        this.player = player;
        this.request = request;
        this.server = connexion();
    }

    private Socket connexion() {
        try {
            SynchroConfig synchroConfig = new SynchroConfig();
            InetAddress serveur = InetAddress.getByName(synchroConfig.getHost());
            return new Socket(serveur, synchroConfig.getPort());
        } catch (Exception e) {
            Histeria.log("Failed to connect to sync server", LogType.ERROR);
        }
        return null;
    }


    public void execute() throws IOException {
        OutputStream output = server.getOutputStream();
        switch (request){
            case GET:

                byte[] response = sendGETRequest();

                break;
            case SEND:

                break;
        }

    }

    private byte[] sendGETRequest() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] packetID = new byte[]{0};
            byte[] playerNameLenght = new byte[]{(byte) (player.getUniqueId().toString().length() & 0xff)};
            byte[] playerUUID = player.getUniqueId().toString().getBytes();

            outputStream.write(ByteBuffer.allocate(4)
                    .putInt(Integer.reverseBytes(packetID.length + playerNameLenght.length + playerUUID.length))
                    .array());

            outputStream.write(packetID);
            outputStream.write(playerNameLenght);
            outputStream.write(playerUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
