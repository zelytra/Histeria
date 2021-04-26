package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.managers.serverSynchro.PacketSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeft implements Listener {

    @EventHandler
    public void OnPlayerLEft(PlayerQuitEvent e){
        PacketSender packetSender = new PacketSender(e.getPlayer());
        packetSender.save();
    }
}
