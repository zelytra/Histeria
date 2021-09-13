package fr.zelytra.histeria.managers.switchServer;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
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
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            String args[] = {"rien"};
            args[0] = serverName;

            PacketSender packetSender = new PacketSender(player);
            packetSender.save();
            playerSwitching.add(player);
            long time = System.currentTimeMillis();
            while (!packetSender.isReceived()) {
                if (System.currentTimeMillis() - time >= 2000) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cFailed to sync inventory please retry later.");
                    return;
                }
            }

            Histeria.log("§a" + player.getName() + " switch to -> " + serverName + " server", LogType.INFO);
            sendPluginMessage("Connect", player, args);
        });
    }

    private void sendPluginMessage(String sub, Player player, String args[]) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sub);
        if (!(args == null)) {
            for (String arg : args) {
                out.writeUTF(arg);
            }
        }
        if (player == null) {
            task = Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> {
                Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                if (p != null) {
                    p.sendPluginMessage(Histeria.getInstance(), "BungeeCord", out.toByteArray());
                }

            }, 0L, 60L);
        } else {
            player.sendPluginMessage(Histeria.getInstance(), "BungeeCord", out.toByteArray());
        }
    }

    public static ArrayList<Player> getPlayerSwitching() {
        return playerSwitching;
    }
}
