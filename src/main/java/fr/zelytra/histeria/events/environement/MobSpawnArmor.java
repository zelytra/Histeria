package fr.zelytra.histeria.events.environement;

import fr.zelytra.histeria.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class MobSpawnArmor implements Listener {

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {

        for (ItemStack armor : e.getEntity().getEquipment().getArmorContents())
            if (Utils.isForbiddenArmorPiece(armor))
                e.setCancelled(true);

    }
}
