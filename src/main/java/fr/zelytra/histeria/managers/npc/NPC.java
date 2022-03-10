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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC implements Serializable {
    public static List<NPC> npcList = new ArrayList<>();

    private final String name;
    private final transient ServerPlayer npc;
    private transient Location location;
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

    private ServerPlayer summonNPC() {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name.replace("&", "§"));

        if (this.skin != null && skin.getTexture() != null) {
            //gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
        }

        ServerPlayer entity = new ServerPlayer(nmsServer, nmsWorld, gameProfile);
        showNPC();

        npcList.add(this);
        return entity;
    }

    public void destroy() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            new NPCPacket(player, npc).hide();
        }

        npcList.remove(this);
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "NPC";
        File file = new File(folderPath + File.separator + name + ".npc");

        if (file.exists())
            file.delete();
    }

    /**
     * Send NPC display packet for all connected player
     */
    public void showNPC() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            showNPCMethod(player);
        }

    }

    public void showNPC(Player player) {
        showNPCMethod(player);
    }

    private void showNPCMethod(Player player) {
        NPCPacket npcPacket = new NPCPacket(player, npc);
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.setSkin(skin), 1);
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.show(), 1);
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.setPosition(location), 1);
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.removeFromTab(), 60);
    }

    public void move(Location location) {
        this.location = location;
        this.Clocation = new CLocation(location);
        for (Player player : Bukkit.getOnlinePlayers())
            new NPCPacket(player, npc).setPosition(location);
    }


    public void setSkin(String url) {

        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            this.skin = new Skin(url);

            if (skin.getTexture() == null) {
                Histeria.log("Failed to download the skin", LogType.ERROR);
                return;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                NPCPacket npcPacket = new NPCPacket(player, npc);
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.hide(), 1);
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.setSkin(skin), 1);
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.show(), 1);
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.setPosition(location), 1);
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> npcPacket.removeFromTab(), 60);
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

    public NPCAction getAction() {
        return action;
    }

    public void setAction(NPCAction action) {
        this.action = action;
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

        Histeria.log(npcList.size() + " npc has been saved", LogType.INFO);
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
                Histeria.log("Wrong NPC file format for : " + file.getName(), LogType.ERROR);
                file.delete();
            }

        }

        Histeria.log(npcList.size() + " npc has been loaded", LogType.INFO);
    }

    public Location getTeleportLocation() {
        return teleportLocation.getLocation();
    }

    public void setTeleportLocation(Location location) {
        this.teleportLocation = new CLocation(location);
    }
}
