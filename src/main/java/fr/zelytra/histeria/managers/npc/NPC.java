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
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.utils.CLocation;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC implements Serializable {
    public static List<NPC> npcList = new ArrayList<>();

    private final String name;
    private final transient EntityPlayer npc;
    private final transient Location location;
    private NPCAction action = NPCAction.DEFAULT;
    private String serverName;

    private CLocation Clocation;
    private CLocation teleportLocation;
    private Skin skin;

    /**
     * Minecraft API for create a custom NPC with different parameter
     *
     * @param location Location of NPC
     * @param npcName  NPC's name
     */

    public NPC(Location location, String npcName) {

        this.name = npcName;
        this.Clocation = new CLocation(location);
        this.location = location;

        this.npc = summonNPC();

    }

    public NPC(Location location, String npcName, NPCAction action, Skin skin, String serverName, CLocation teleportLocation) {

        this.action = action;
        this.name = npcName;
        this.Clocation = new CLocation(location);
        this.location = location;
        this.teleportLocation = teleportLocation;
        this.skin = skin;

        this.npc = summonNPC();
        this.serverName = serverName;

    }

    private EntityPlayer summonNPC() {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name.replace("&", "§"));

        if (this.skin != null && skin.getTexture() != null) {
            //gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
        }

        EntityPlayer entity = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        npcList.add(this);
        return entity;
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
            npc.setLocation(location.getBlockX() + 0.5, location.getY(), location.getBlockZ() + 0.5, location.getYaw(), location.getPitch());
            connection.sendPacket(new PacketPlayOutEntityTeleport(npc));

        }
    }

    public void setSkin(String url) {

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            this.skin = new Skin(url);

            if (skin.getTexture() == null) {
                Histeria.log("§cFailed to download the skin", LogType.ERROR);
                return;
            }

            GameProfile gameProfile = npc.getProfile();
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));


            for (Player player : Bukkit.getOnlinePlayers()) {
                removeNPCPacket(player);

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

    public CLocation getCLocation() {
        return Clocation;
    }

    public String getName() {
        return name;
    }

    public int getEntityID() {
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

        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

            DataWatcher watcher = npc.getDataWatcher();
            watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, true));
            Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc)), 1);
        }, 20);

    }


    public String getServer() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public static void saveAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "NPC";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdir();

        for (NPC npc : npcList) {

            try {

                File file = new File(folderPath + File.separator + npc.getName() + ".npc");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(npc);
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Histeria.log("§6" + npcList.size() + "§a npc has been saved", LogType.INFO);
    }

    public static void loadAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "NPC";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
            return;
        }

        for (File file : folder.listFiles()) {

            try {
                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                NPC npcUnserialize = (NPC) oos.readObject();
                new NPC(npcUnserialize.getCLocation().getLocation(), npcUnserialize.name, npcUnserialize.action, npcUnserialize.skin, npcUnserialize.serverName, npcUnserialize.teleportLocation);
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Histeria.log("§6" + npcList.size() + "§a npc has been loaded", LogType.INFO);
    }

    public Location getTeleportLocation() {
        return teleportLocation.getLocation();
    }

    public void setTeleportLocation(Location location) {
        this.teleportLocation = new CLocation(location);
    }
}
