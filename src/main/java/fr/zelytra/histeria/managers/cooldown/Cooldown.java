package fr.zelytra.histeria.managers.cooldown;

import fr.zelytra.histeria.utils.Message;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class Cooldown {
    private static final HashMap<Cooldown, Player> cooldownsList = new HashMap<>();

    private final Player player;
    private final long checkTime;

    private final String tag;

    /**
     * Init a player cooldown
     *
     * @param p           Player link to the cooldown
     * @param timeSeconds Time before removing cooldown in seconds
     * @param tag         Unique tag which permit to stack different cooldown on the same player
     */

    public Cooldown(Player p, long timeSeconds, String tag) {
        this.player = p;
        this.checkTime = System.currentTimeMillis() + timeSeconds * 1000;
        this.tag = tag;
        cooldownsList.put(this, p);
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getTag() {
        return tag;
    }

    public boolean isFinished() {
        return this.checkTime - System.currentTimeMillis() <= 0;
    }

    @Override
    public String toString() {
        //Milliseconds display
        if (this.checkTime - System.currentTimeMillis() <= 1000) {
            return "§e" + (this.checkTime - System.currentTimeMillis()) + "§6ms";
        }
        //Seconds display
        else if (this.checkTime - System.currentTimeMillis() <= 60000) {
            int timeInSec = (int) ((this.checkTime - System.currentTimeMillis()) / 1000) % 60;
            return "§e" + timeInSec + "§6s";
        }
        //Minutes display
        else if (this.checkTime - System.currentTimeMillis() <= 3600000) {
            int remainingSec = (int) ((this.checkTime - System.currentTimeMillis()) / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            return "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        }
        //Hours display
        else if (this.checkTime - System.currentTimeMillis() <= 86400000) {
            int remainingSec = (int) ((this.checkTime - System.currentTimeMillis()) / 1000);
            int timeInSec = (remainingSec % 60);
            int timeInMin = (remainingSec % 3600) / 60;
            int TimeInHour = (remainingSec % 86400) / 3600;
            return "§e" + TimeInHour + "§6h" + "§e" + timeInMin + "§6m" + "§e" + timeInSec + "§6s";
        } else {
            return "§cNA";
        }

    }

    /**
     * @param player Player to check
     * @param tag    Tag of the cooldown
     * @return true if cooldown ended and false if cooldown is running
     * if return is false, it will print the time remaining to the player
     */

    public static boolean cooldownCheck(Player player, String tag) {
        Cooldown toRemove = null;
        for (Map.Entry<Cooldown, Player> entry : cooldownsList.entrySet()) {
            if (entry.getKey().getTag().equalsIgnoreCase(tag) && entry.getValue().getUniqueId() == player.getUniqueId()) {
                toRemove = entry.getKey();
                if (!toRemove.isFinished()) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§6You need to wait " + toRemove);
                    return false;
                }

            }
        }
        cooldownsList.remove(toRemove);
        return true;
    }

}
