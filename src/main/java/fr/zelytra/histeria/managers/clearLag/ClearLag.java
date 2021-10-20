package fr.zelytra.histeria.managers.clearLag;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.market.stand.Stand;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;

public class ClearLag {
    private final int repeatTime = 1200; //Time in second
    private final int cd = 10; //Time in second

    public ClearLag() {
        startTask();
    }

    private void startTask() {
        Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), this::run, repeatTime * 20, repeatTime * 20);
    }

    public void run() {
        LangMessage.broadcast(Message.HISTALERT.getMsg(), "clearLag.warning", "");
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {
            int amount = clearItem();
            LangMessage.broadcast(Message.HISTALERT + "§b" + amount + " §3", "clearLag.clearItem", "");
        }, cd * 20);

    }

    private int clearItem() {
        int count = 0;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.DROPPED_ITEM) {
                    System.out.println(((Item) entity).getItemStack());
                    if (Stand.getStand(((Item) entity).getItemStack()) != null)
                        continue;
                    entity.remove();
                    count++;
                }
            }
        }
        return count;
    }
}
