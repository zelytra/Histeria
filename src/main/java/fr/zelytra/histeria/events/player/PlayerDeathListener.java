package fr.zelytra.histeria.events.player;

import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        CustomPlayer.getCustomPlayer(e.getEntity().getName()).addDeath();

        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player)
            CustomPlayer.getCustomPlayer(e.getEntity().getKiller().getName()).addKill();

    }
}
