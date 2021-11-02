/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.switchServer;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.server.PMessage;
import fr.zelytra.histeria.managers.server.SubChannel;
import fr.zelytra.histeria.managers.serverSynchro.PacketSender;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class SwitchServer {
    private static BukkitTask task;
    private Player player;
    private static ArrayList<Player> playerSwitching = new ArrayList<>();

    public SwitchServer(Player player) {
        this.player = player;
    }

    public void switchTo(String serverName) {

        if(Histeria.server.getServerName().equalsIgnoreCase(serverName)){
            LangMessage.sendMessage(player,"server.alreadyOnTheServer");
            return;
        }
        
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            String args[] = new String[1];
            args[0] = serverName;

            PacketSender packetSender = new PacketSender(player);
            packetSender.save();
            playerSwitching.add(player);

            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
            customPlayer.saveData();

            long time = System.currentTimeMillis();

            while (!packetSender.isReceived()) {
                if (System.currentTimeMillis() - time >= 2000) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cFailed to sync inventory please retry later.");
                    return;
                }
            }

            Histeria.log("§a" + player.getName() + " switch to -> " + serverName + " server", LogType.INFO);
            new PMessage(SubChannel.CONNECT, player, args);
        });
    }



    public static ArrayList<Player> getPlayerSwitching() {
        return playerSwitching;
    }
}
