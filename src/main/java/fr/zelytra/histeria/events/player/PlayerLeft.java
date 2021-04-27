package fr.zelytra.histeria.events.player;


import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeft implements Listener {

    @EventHandler
    public void OnPlayerLEft(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            if (SwitchServer.getPlayerSwitching().contains(e.getPlayer())) {
                SwitchServer.getPlayerSwitching().remove(e.getPlayer());
                return;
            }

        });

    }
}
