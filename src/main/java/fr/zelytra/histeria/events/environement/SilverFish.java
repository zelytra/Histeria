package fr.zelytra.histeria.events.environement;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SilverFish implements Listener {
    @EventHandler
    public void onSilverFishSpawn(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.SILVERFISH) {
            e.setCancelled(true);
        }
    }
}
