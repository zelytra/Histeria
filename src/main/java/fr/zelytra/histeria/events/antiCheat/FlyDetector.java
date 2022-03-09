package fr.zelytra.histeria.events.antiCheat;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FlyDetector implements Listener {

    @EventHandler
    public void playerSpeed(PlayerMoveEvent e) {
        if (Utils.canByPass(e.getPlayer())) return;

        double speed = e.getFrom().distance(e.getTo()) * 20;
        if (speed >= 50.0) {

            if (!Cooldown.cooldownCheck(e.getPlayer(), "flyDetector", false))
                return;
            new Cooldown(e.getPlayer(), 5, "flyDetector");


            String format = "0.00";
            NumberFormat formatter = new DecimalFormat(format);
            Histeria.log(e.getPlayer().getName() + " move at " + formatter.format(speed) + " block/s", LogType.WARN);
            new DiscordLog(WebHookType.CHEATER, "**" + e.getPlayer().getName() + "**" + " move at **" + formatter.format(speed) + " block/s** on server " + Histeria.server.getServerName().replace("§ca", ""));
        }


    }

}
