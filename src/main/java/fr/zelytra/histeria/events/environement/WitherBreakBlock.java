package fr.zelytra.histeria.events.environement;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.ArrayList;

public class WitherBreakBlock implements Listener {

    @EventHandler
    public void onBlockEat(EntityChangeBlockEvent eat) {
        ArrayList<Material> whitelist = new ArrayList<>();
        whitelist.add(Material.LODESTONE);
        whitelist.add(Material.ENCHANTING_TABLE);
        whitelist.add(Material.ENDER_CHEST);

        if (eat.getEntity().getType() == EntityType.WITHER_SKULL && whitelist.contains(eat.getBlock().getType())) {
            eat.setCancelled(true);
        }
        if (eat.getEntity().getType() == EntityType.WITHER && whitelist.contains(eat.getBlock().getType())) {
            eat.setCancelled(true);
        }

    }
}
