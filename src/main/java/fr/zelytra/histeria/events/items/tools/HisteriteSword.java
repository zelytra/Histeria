package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HisteriteSword implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_SWORD;

    @EventHandler
    public void swordAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), (Player) e.getDamager())) {
            Player player = (Player) e.getDamager();
            DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);
            durabilityHandler.iterate();

        }
    }
}
