package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftData implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void OnPlayerLEft(PlayerQuitEvent e) {
        if (!SwitchServer.getPlayerSwitching().contains(e.getPlayer())) {
            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
            customPlayer.saveData();
            customPlayer.destroy();
        }

    }
}
