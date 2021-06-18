package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinData implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {

        new CustomPlayer(e.getPlayer());

    }
}
