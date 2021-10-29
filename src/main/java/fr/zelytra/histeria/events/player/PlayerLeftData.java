package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftData implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerLEft(PlayerQuitEvent e) {

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        customPlayer.saveData();
        customPlayer.destroy();

    }
}
