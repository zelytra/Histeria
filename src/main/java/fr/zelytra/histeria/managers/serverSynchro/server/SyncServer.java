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
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.serverSynchro.builder.Capsule;
import fr.zelytra.histeria.managers.serverSynchro.builder.PlayerData;
import fr.zelytra.histeria.managers.serverSynchro.builder.SynchroConfig;
import fr.zelytra.histeria.managers.serverSynchro.contents.*;
import fr.zelytra.histeria.utils.timer.Timer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SyncServer {

    private static int timeOut = 2;

    private final Request request;
    private final Player player;
    private final Socket server;

    public boolean isCommunicationOver = false;
    public boolean isNewPlayer = false;

    public SyncServer(Player player, Request request) {
        this.player = player;
        this.request = request;
        this.server = connexion();
    }

    public void execute() {
        try {

            // Init communication stream
            OutputStream output = server.getOutputStream();
            InputStream input = server.getInputStream();

            switch (request) {
                case GET:
                    Histeria.log("Requesting " + player.getName() + " data...", LogType.INFO);
                    Timer timer = new Timer();
                    // Sending GET request
                    List<Capsule> capsuleList = new ArrayList<>();
                    capsuleList.add(new IDCapsule(request));
                    capsuleList.add(new UUIDCapsule(player));
                    sendCapsule(output, capsuleList);

                    // Getting server status response
                    byte[] response = new byte[4];
                    input.read(response);

                    byte[] id = new byte[1];
                    input.read(id);

                    // If player don't find in server
                    if (id[0] == (byte) 254) {
                        Histeria.log(player.getName() + " is a new player, nothing to do §7[" + timer.stop() + "]", LogType.INFO);
                        server.close();
                        isCommunicationOver = true;
                        isNewPlayer = true;
                        return;
                    }

                    // Reading data sent
                    PlayerData playerData = new PlayerData(player);

                    // Building capsules
                    capsuleList = new ArrayList<>();
                    capsuleList.add(new InventoryCapsule());
                    capsuleList.add(new EChestCapsule());
                    capsuleList.add(new HealthCapsule());
                    capsuleList.add(new FoodCapsule());
                    capsuleList.add(new XPCapsule());
                    capsuleList.add(new EffectCapsule());
                    capsuleList.add(new VanishCapsule());
                    capsuleList.add(new HomeCapsule());
                    capsuleList.add(new GameModeCapsule());

                    // Reading data and uncaps
                    for (Capsule capsule : capsuleList) {
                        byte[] buffer = new byte[capsule.firstPacketSize()];
                        input.read(buffer);
                        playerData = capsule.uncaps(playerData, buffer, input);
                    }

                    playerData.buildPlayer();
                    Histeria.log(player.getName() + " inventory's has been synchronised §7[" + timer.stop() + "]", LogType.INFO);
                    isCommunicationOver = true;
                    break;

                case SEND:
                    Histeria.log(player.getName() + " inventory's send to the server...", LogType.INFO);
                    timer = new Timer();
                    // Building capsules
                    capsuleList = new ArrayList<>();
                    capsuleList.add(new IDCapsule(request));
                    capsuleList.add(new UUIDCapsule(player));
                    capsuleList.add(new InventoryCapsule(player));
                    capsuleList.add(new EChestCapsule(player));
                    capsuleList.add(new HealthCapsule(player));
                    capsuleList.add(new FoodCapsule(player));
                    capsuleList.add(new XPCapsule(player));
                    capsuleList.add(new EffectCapsule(player));
                    capsuleList.add(new VanishCapsule(player));
                    capsuleList.add(new HomeCapsule(CustomPlayer.getCustomPlayer(player.getName())));
                    capsuleList.add(new GameModeCapsule(player));

                    // Sending capsules
                    sendCapsule(output, capsuleList);

                    // Getting server response status
                    response = new byte[4];
                    input.read(response);
                    response = new byte[ByteBuffer.wrap(response).getInt()];
                    input.read(response);

                    if (Integer.reverseBytes(ByteBuffer.wrap(response).getInt()) != 1) {
                        Histeria.log("Inventory not received §7[" + timer.stop() + "]", LogType.WARN);
                        server.close();
                        return;
                    }

                    isCommunicationOver = true;
                    Histeria.log("Inventory received §7[" + timer.stop() + "]", LogType.INFO);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCapsule(OutputStream output, List<Capsule> capsuleList) {
        try {

            int messageLength = 0;
            for (Capsule capsule : capsuleList)
                messageLength += capsule.getSize();

            output.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(messageLength)).array());

            for (Capsule capsule : capsuleList)
                output.write(capsule.build());

        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
