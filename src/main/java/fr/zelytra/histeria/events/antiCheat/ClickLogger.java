package fr.zelytra.histeria.events.antiCheat;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class ClickLogger implements Listener {

    private static List<PlayerClick> playersClick = new ArrayList<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        PlayerClick playerClick = getPlayerClick(e.getPlayer());

        if (playerClick == null) {
            playerClick = new PlayerClick(e.getPlayer().getName());
            playersClick.add(playerClick);
        }

        playerClick.click();

    }

    public static void clickerTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Histeria.getInstance(), () -> {
            for (PlayerClick playerClick : playersClick) {

                if (playerClick.getCount() >= 12) {
                    Histeria.log(playerClick.getName() + " at " + playerClick.getCount() + " CPS", LogType.WARN);
                    new DiscordLog(WebHookType.CHEATER, "**" + playerClick.getName() + "** at **" + playerClick.getCount() + " CPS** on server " + Histeria.server.getServerName().replace("§ca",""));
                }

                playerClick.reset();
            }
        }, 0, 20);
    }

    private PlayerClick getPlayerClick(Player player) {
        for (PlayerClick playerClick : playersClick)
            if (playerClick.getName().equalsIgnoreCase(player.getName()))
                return playerClick;
        return null;
    }
}

class PlayerClick {
    private final String name;
    private int count = 0;

    public PlayerClick(String name) {
        this.name = name;
    }

    public void click() {
        count++;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }

}
