package fr.zelytra.histeria.commands.message;

import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PrivateMessageListener implements Listener {

    @EventHandler
    public void onPrivateMessage(PlayerCommandPreprocessEvent e) {

        if (e.getMessage().contains("/me") || e.getMessage().contains("/tell") || e.getMessage().contains("/whisper") || e.getMessage().equalsIgnoreCase("/bukkit:ver")) {
            LangMessage.sendMessage(e.getPlayer(), "command.noPermission");
            e.setCancelled(true);
        }
        if (e.getMessage().contains("/msg")) {
            e.setCancelled(true);
            new PrivateMessage(e.getPlayer(), e.getMessage()).send();
        }
    }
}
