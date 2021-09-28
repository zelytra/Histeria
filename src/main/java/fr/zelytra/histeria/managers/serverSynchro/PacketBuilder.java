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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;

public class PacketBuilder {
    private Player player;

    public  PacketBuilder(Player player) {
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
            byte[] effectLength = ByteBuffer.allocate(4).putInt(playerInventory.length).array();

            byte[] packetID = new byte[] { 1 };
            byte[] playerNameLenght = new byte[] { (byte) (player.getUniqueId().toString().length() & 0xff) };
            byte[] playerUUID = player.getUniqueId().toString().getBytes();
            outputStream.write(ByteBuffer.allocate(4)
                    .putInt(Integer.reverseBytes(packetID.length + playerNameLenght.length + playerUUID.length
                            + invLength.length + playerInventory.length + enderLength.length + playerEnderChest.length
                            + playerHealth.length + playerFood.length + playerXP.length + playerEffect.length
                            + effectLength.length)).array());
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

        } catch (IOException e) {
            Histeria.log("§cFailed to build the byte message", LogType.ERROR);
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

    private  byte[] potionArrayToBase64(Collection<PotionEffect> potion) throws IllegalStateException {
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
