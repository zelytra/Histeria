package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.ArrayList;

public class WitherBreakBlock implements Listener {

    @EventHandler
    public void onBlockEat(EntityChangeBlockEvent eat) {
        ArrayList<Material> whitelist = new ArrayList<>();
        whitelist.add(CustomMaterial.REINFORCED_OBSIDIAN.getVanillaMaterial());
        whitelist.add(Material.ENCHANTING_TABLE);
        whitelist.add(Material.ENDER_CHEST);

        if (eat.getEntity().getType() == EntityType.WITHER_SKULL && whitelist.contains(eat.getBlock().getType())) {
            eat.setCancelled(true);
        }
        if (eat.getEntity().getType() == EntityType.WITHER && whitelist.contains(eat.getBlock().getType())) {
            eat.setCancelled(true);
        }

    }

    @EventHandler
    public void onWitherSpawn(ExplosionPrimeEvent e){
        if(e.getEntity().getType()==EntityType.WITHER){
            e.setCancelled(true);
        }
    }
}
