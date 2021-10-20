package fr.zelytra.histeria.commands.vanish;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListener implements Listener {

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        if (Vanish.vanishedPlayers.contains(e.getPlayer().getName())) {
            Vanish.vanishedPlayers.remove(e.getPlayer().getName());

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!Utils.canByPass(p))
                    p.showPlayer(Histeria.getInstance(), e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        for (String playerName : Vanish.vanishedPlayers) {
            if (!Utils.canByPass(e.getPlayer()))
                e.getPlayer().hidePlayer(Histeria.getInstance(), Bukkit.getPlayer(playerName));
        }

    }
}
