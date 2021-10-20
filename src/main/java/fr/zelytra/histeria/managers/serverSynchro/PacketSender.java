/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class PacketSender {
    private boolean isReceived = false;
    private Player player;

    public PacketSender(Player player) {
        this.player = player;

    }

    public void save() {
        try {
            //Sending
            Socket socket = connexion();
            if(socket==null){
                return;
            }
            OutputStream output = socket.getOutputStream();
            PacketBuilder playerPacket = new PacketBuilder(this.player);
            output.write(playerPacket.build());
            Histeria.log("§e" + this.player.getName() + " inventory's send to the server...", LogType.INFO);

            //Confirm
            InputStream input = socket.getInputStream();
            byte[] Rbyte = new byte[4];
            input.read(Rbyte);
            int messageLength = Integer.reverseBytes(ByteBuffer.wrap(Rbyte).getInt());
            byte[] Mbyte = new byte[messageLength];
            input.read(Mbyte);
            if (Integer.reverseBytes(ByteBuffer.wrap(Rbyte).getInt()) != 1) {
                Histeria.log("§cInventory not received.",LogType.WARN);
                socket.close();
                return;
            }
            Histeria.log("§eInventory received.",LogType.INFO);
            this.isReceived =true;
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isReceived() {
        return this.isReceived;
    }

    private Socket connexion() {
        try {
            SynchroConfig synchroConfig = new SynchroConfig();

            InetAddress serveur = InetAddress.getByName(synchroConfig.getHost());
            return new Socket(serveur, synchroConfig.getPort());
        } catch (Exception e) {
            Histeria.log("§cFailed to connect to sync server",LogType.ERROR);
        }
        return null;
    }
}
