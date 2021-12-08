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
import fr.zelytra.histeria.managers.home.Home;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class PacketBuilder {
    private Player player;

    public PacketBuilder(Player player) {
        this.player = player;
    }

    public byte[] build() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] playerInventory = itemStackArrayToBase64(player.getInventory().getContents());
            byte[] invLength = ByteBuffer.allocate(4).putInt(playerInventory.length).array();


            byte[] playerEnderChest = itemStackArrayToBase64(player.getEnderChest().getContents());
            byte[] enderLength = ByteBuffer.allocate(4).putInt(playerEnderChest.length).array();

            byte[] playerHealth = ByteBuffer.allocate(8).putDouble(player.getHealth()).array();

            byte[] playerFood = ByteBuffer.allocate(4).putInt(player.getFoodLevel()).array();

            byte[] playerXP = ByteBuffer.allocate(4).putInt(player.getLevel()).array();

            byte[] playerEffect = potionArrayToBase64(player.getActivePotionEffects());
            byte[] effectLength = ByteBuffer.allocate(4).putInt(playerEffect.length).array();

            /* Home part*/
            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
            Home home = null;
            int homePacketSize = 0;

            byte[] homeX = new byte[4], homeY = new byte[4], homeZ = new byte[4], homeWorldSize = new byte[4], homeWorld = new byte[0];

            for (Home h : customPlayer.getHomes()) {
                if (h.hasTpServerRequest())
                    home = h;
            }

            if (home != null) {
                homeX = ByteBuffer.allocate(4).putInt((int) home.getLocation().getX()).array();
                homeY = ByteBuffer.allocate(4).putInt((int) home.getLocation().getY()).array();
                homeZ = ByteBuffer.allocate(4).putInt((int) home.getLocation().getZ()).array();

                if (home.getLocation().getWorld() == null)
                    homeWorld = home.getWorldName().getBytes(StandardCharsets.UTF_8);
                else
                    homeWorld = home.getLocation().getWorld().getName().getBytes(StandardCharsets.UTF_8);

                homeWorldSize = ByteBuffer.allocate(4).putInt(homeWorld.length).array();


                homePacketSize = homeZ.length * 4 + homeWorld.length;
            }

            byte[] packetID = new byte[]{1};
            byte[] playerNameLenght = new byte[]{(byte) (player.getUniqueId().toString().length() & 0xff)};
            byte[] playerUUID = player.getUniqueId().toString().getBytes();

            outputStream.write(ByteBuffer.allocate(4).putInt(Integer.reverseBytes(packetID.length +
                    playerNameLenght.length +
                    playerUUID.length +
                    invLength.length +
                    playerInventory.length +
                    enderLength.length +
                    playerEnderChest.length +
                    playerHealth.length +
                    playerFood.length +
                    playerXP.length +
                    playerEffect.length +
                    effectLength.length +
                    homePacketSize)).array());

            // Packet construction
            outputStream.write(packetID);
            outputStream.write(playerNameLenght);
            outputStream.write(playerUUID);
            outputStream.write(invLength);
            outputStream.write(playerInventory);
            outputStream.write(enderLength);
            outputStream.write(playerEnderChest);
            outputStream.write(playerHealth);
            outputStream.write(playerFood);
            outputStream.write(playerXP);
            outputStream.write(effectLength);
            outputStream.write(playerEffect);

            if (homePacketSize != 0) {
                outputStream.write(homeX);
                outputStream.write(homeY);
                outputStream.write(homeZ);
                outputStream.write(homeWorldSize);
                outputStream.write(homeWorld);
            }

        } catch (IOException e) {
            Histeria.log("Failed to build the byte message", LogType.ERROR);
        }
        return outputStream.toByteArray();
    }

    private byte[] itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            dataOutput.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    private byte[] potionArrayToBase64(Collection<PotionEffect> potion) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(potion.size());

            for (PotionEffect p : potion) {
                byte[] type = ByteBuffer.allocate(4).putInt(p.getType().getId()).array();
                dataOutput.write(type);
                byte[] time = ByteBuffer.allocate(4).putInt(p.getDuration()).array();
                dataOutput.write(time);
                byte[] level = ByteBuffer.allocate(4).putInt(p.getAmplifier()).array();
                dataOutput.write(level);
            }

            dataOutput.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save potion.", e);
        }
    }


}
