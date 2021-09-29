package fr.zelytra.histeria.events.items.projectile;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemLaunchEvent;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Shuriken implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.SHURIKEN;

    @EventHandler
    public void launch(CustomItemLaunchEvent e) {

        if(e.isCancelled()) return;

        if (e.getMaterial() == customMaterial)
            e.getEvent().getEntity().setCustomName(customMaterial.getName());

    }

    @EventHandler
    public void hit(ProjectileHitEvent e) {
        if (e.getEntity().getCustomName() == null || e.getHitEntity() == null) {
            return;
        }
        if (e.getEntity().getCustomName().equals(customMaterial.getName())) {
            if (e.getHitEntity() instanceof LivingEntity) {
                ((LivingEntity) e.getHitEntity()).damage(4.0);

            }
        }

    }
}
