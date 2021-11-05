/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.zelytra.histeria.Histeria;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {
    public static List<NPC> npcList = new ArrayList<>();

    private final String name;
    private final EntityPlayer npc;
    private NPCAction action = NPCAction.DEFAULT;
    private String serverName;

    /**
     * Minecraft API for create a custom NPC with different parameter
     *
     * @param location Location of NPC
     * @param npcName  NPC's name
     */

    public NPC(Location location, String npcName) {

        this.name = npcName;


        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcName);
        this.npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        npcList.add(this);
    }

    public void destroy() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
        }
        npcList.remove(this);
    }

    /**
     * Send NPC display packet for all connected player
     */
    public void showNPC() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            addNPCPacket(player);

        }

    }

    public void showNPC(Player player) {

        addNPCPacket(player);

    }

    public void move(Location location) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            PlayerConnection connection = getPlayerConnection(player);
            npc.setLocation(location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5, location.getYaw(), location.getPitch());
            connection.sendPacket(new PacketPlayOutEntityTeleport(npc));

        }
    }

    public void setSkin(String url) {

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            Skin skin = new Skin(url);
            GameProfile gameProfile = npc.getProfile();
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
            System.out.println("propertie applied");

            for (Player player : Bukkit.getOnlinePlayers()) {
                removeNPCPacket(player);
                System.out.println("Sending packet to player " + player.getName());
                DataWatcher watcher = npc.getDataWatcher();
                watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
                PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getId(), watcher, true);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

                addNPCPacket(player);

            }

        });

    }

    public Location getLocation() {
        return npc.getBukkitEntity().getLocation();
    }

    public String getName() {
        return name;
    }

    public int getEntityID(){
        return this.npc.getId();
    }

    public static NPC getNPC(String name) {

        for (NPC npc : npcList)
            if (npc.getName().equalsIgnoreCase(name))
                return npc;

        return null;
    }

    private PlayerConnection getPlayerConnection(Player player) {

        return ((CraftPlayer) player).getHandle().playerConnection;

    }

    public NPCAction getAction() {
        return action;
    }

    public void setAction(NPCAction action) {
        this.action = action;
    }

    private void removeNPCPacket(Player player) {

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));

    }

    private void addNPCPacket(Player player) {

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)), 1);

    }


    public String getServer() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
