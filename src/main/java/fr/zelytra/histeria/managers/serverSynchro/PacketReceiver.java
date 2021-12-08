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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class PacketReceiver {
    private final Player player;
    private PlayerDat playerDat;
    private boolean isNew = false;
    private boolean isLoaded = false;

    public PacketReceiver(Player player) {
        this.player = player;
        this.playerDat = new PlayerDat(player);
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

    public void requestPlayer() {
        try {
            Socket socket = connexion();
            // Righting message
            byte[] Sbyte = requestDatBuilder();
            if (socket == null) {
                return;
            }
            OutputStream output = socket.getOutputStream();

            // Sending message
            output.write(Sbyte);

            // Reading length message 4 first bytes
            InputStream input = socket.getInputStream();
            byte[] Rbyte = new byte[4];
            input.read(Rbyte);
            byte[] id = new byte[1];
            input.read(id);

            if (id[0] == (byte) 254) {
                Histeria.log(player.getName() + " is a new player. Nothing to do.", LogType.INFO);
                socket.close();
                this.isNew = true;
                return;
            }
            // [inv length] [inventory] [enderLength] [enderChest] [health] [food] [xp]
            // [potionLength] [potionEffect]

            // Inventory
            byte[] invSize = new byte[4];
            input.read(invSize);
            int InvLengh = ByteBuffer.wrap(invSize).getInt();
            byte[] inventory = new byte[InvLengh];
            input.read(inventory);

            playerDat.setInventory(itemStackArrayFromBase64(inventory));

            // EnderChest
            byte[] endersize = new byte[4];
            input.read(endersize);
            int enderLengh = ByteBuffer.wrap(endersize).getInt();
            byte[] enderChest = new byte[enderLengh];
            input.read(enderChest);

            playerDat.setEnderChest(itemStackArrayFromBase64(enderChest));

            // Health
            byte[] health = new byte[8];
            input.read(health);
            playerDat.setHealth(ByteBuffer.wrap(health).getDouble());

            // Food
            byte[] food = new byte[4];
            input.read(food);
            playerDat.setFood(ByteBuffer.wrap(food).getInt());

            // XP
            byte[] xp = new byte[4];
            input.read(xp);
            playerDat.setXp(ByteBuffer.wrap(xp).getInt());

            // Effect
            byte[] effectSize = new byte[4];
            input.read(effectSize);
            int effectLength = ByteBuffer.wrap(effectSize).getInt();
            byte[] potionEffect = new byte[effectLength];
            input.read(potionEffect);
            playerDat.setEffects(potionArrayFromBase64(potionEffect));

            // Home
            byte[] coordinateSize = new byte[4];
            int homeResponse = input.read(coordinateSize);

            if (homeResponse != -1) {
                int x = ByteBuffer.wrap(coordinateSize).getInt();
                input.read(coordinateSize);
                int y = ByteBuffer.wrap(coordinateSize).getInt();
                input.read(coordinateSize);
                int z = ByteBuffer.wrap(coordinateSize).getInt();

                input.read(coordinateSize);
                byte[] worldNameSize = new byte[ByteBuffer.wrap(coordinateSize).getInt()];

                input.read(worldNameSize);
                String worldName = new String(worldNameSize, StandardCharsets.UTF_8);
                playerDat.setTeleportTask(new Location(Bukkit.getWorld(worldName), x, y, z));
            }


            Histeria.log(player.getName() + " inventory's has been synchronised.", LogType.INFO);
            this.isLoaded = true;
            socket.close();
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public boolean receiveFinish() {
        return this.isLoaded;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void buildPlayer() {
        playerDat.buildPlayer();
    }

    private byte[] requestDatBuilder() {

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

    private ItemStack[] itemStackArrayFromBase64(byte[] data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    private PotionEffect[] potionArrayFromBase64(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        int lengthArray = dataInput.readInt();
        PotionEffect[] potion = new PotionEffect[lengthArray];

        // Read the serialized inventory
        for (int i = 0; i < potion.length; i++) {
            potion[i] = new PotionEffect(PotionEffectType.getById(dataInput.readInt()), dataInput.readInt(), dataInput.readInt());
        }

        dataInput.close();
        return potion;
    }

}
