/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.afk;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.GroupFX;
import fr.zelytra.histeria.utils.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Afk {


    private Location currentLocation;
    private long lastSeenTime;
    private boolean afk;

    private final static int afkTime = 300; //AFK limit in seconds
    private final static int kickTime = 600; //AFK Kick limit in seconds
    private final static int maxPlayer = 50; //AFK kick player limit

    public Afk(Player player) {

        this.currentLocation = player.getLocation();
        this.lastSeenTime = System.currentTimeMillis();
        this.afk = false;

    }


    public boolean isAFK() {
        return afk;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setLastSeenTime(long lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public long getLastSeenTime() {
        return lastSeenTime;
    }

    public static void startAfkListener() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Histeria.getInstance(), () -> {
            for (CustomPlayer customPlayer : CustomPlayer.getList()) {
                Afk playerAFK = customPlayer.getAfk();

                if (customPlayer.getPlayer() == null) return;

                if ((System.currentTimeMillis() - playerAFK.lastSeenTime) > afkTime * 1000 && !playerAFK.afk) {
                    playerAFK.setAfk(true);
                    LangMessage.broadcast("§7" + customPlayer.getPlayer().getName() + " ", "afk.isAfk", "");

                    GroupFX groupFX = GroupFX.getByName(Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(customPlayer.getPlayer()).getPrimaryGroup());
                    customPlayer.getPlayer().playerListName(Component.text()
                            .content(" " + groupFX.getBadge().toString())
                            .append(Component.text().content(" " + customPlayer.getPlayer().getName()).color(groupFX.getNameColor()))
                            .append(Component.text().content(customPlayer.isAFK() ? " §7[AFK]" : ""))
                            .build());
                }

                if ((System.currentTimeMillis() - playerAFK.lastSeenTime) > kickTime * 1000 && Bukkit.getOnlinePlayers().size() > maxPlayer)
                    customPlayer.getPlayer().kickPlayer(Message.PLAYER_PREFIX + customPlayer.getLang().get("afk.kick"));


            }
        }, 0, (afkTime * 20) / 4);
    }

    public boolean wasAFK(Player player) {
        boolean lastCheck = afk;
        afkUpdate(player);
        return lastCheck && !afk;
    }

    private void afkUpdate(Player player) {
        if (!player.getLocation().equals(this.currentLocation)) {
            this.setAfk(false);
            this.setCurrentLocation(player.getLocation());
            this.setLastSeenTime(System.currentTimeMillis());
        } else {
            if ((System.currentTimeMillis() - this.lastSeenTime) > afkTime) {
                this.setAfk(true);
            }
        }
    }
}
