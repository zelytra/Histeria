package fr.zelytra.histeria.managers.pvp;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PvP {

    private List<PvP> pvpList = new ArrayList<>();

    private final int cooldown = 15; //Time in seconds

    private BukkitTask task;
    private final CustomPlayer customPlayer;
    private boolean isPvP = false;

    public PvP(CustomPlayer customPlayer) {
        this.customPlayer = customPlayer;
        this.pvpList.add(this);


    }

    public void setPvP() {
        if (!isPvP) {
            LangMessage.sendMessage(customPlayer.getPlayer(), Message.HISTLOGGER.getMsg(), "pvp.inPvP", "");
            isPvP = true;
        } else if (task != null && !task.isCancelled())
            task.cancel();

        startPvPTracker();

    }

    public void removePvP() {
        if (isPvP)
            isPvP = false;
    }

    public boolean isPvP() {
        return isPvP;
    }

    public void killInventory() {
        Player player = customPlayer.getPlayer();

        ItemStack[] pInventory = player.getInventory().getContents();
        player.getInventory().clear();

        for (ItemStack item : pInventory)
            if (item != null)
                player.getWorld().dropItem(player.getLocation(), item);

    }

    private void startPvPTracker() {
        this.task = Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
            this.isPvP = false;
            if (customPlayer.getPlayer() != null)
                LangMessage.sendMessage(customPlayer.getPlayer(), Message.HISTLOGGER.getMsg(), "pvp.outPvP", "");

            task.cancel();
        }, cooldown * 20);
    }
}
