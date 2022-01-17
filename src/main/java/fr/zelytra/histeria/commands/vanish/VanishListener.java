package fr.zelytra.histeria.commands.vanish;

import fr.zelytra.histeria.managers.switchServer.SwitchServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {

        if (Vanish.isVanished(e.getPlayer()) && !SwitchServer.getPlayerSwitching().contains(e.getPlayer()))
            Vanish.unvanish(e.getPlayer());

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (Vanish.isVanished(e.getPlayer()))
            Vanish.vanish(e.getPlayer());

    }
}
