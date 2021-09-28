package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class AlpinHook implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.ALPIN_HOOK;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                Player player = e.getPlayer();
                CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
                Bukkit.getPluginManager().callEvent(customItemEvent);

                if(customItemEvent.isCancelled()){
                    return;
                }
                if (e.getClickedBlock().getLocation().getX() - player.getLocation().getX() <= 1
                        && e.getClickedBlock().getLocation().getX() + 0.5 - player.getLocation().getX() >= -1
                        && e.getClickedBlock().getLocation().getY() - player.getLocation().getY() <= 2
                        && e.getClickedBlock().getLocation().getY() - player.getLocation().getY() >= 0
                        && e.getClickedBlock().getLocation().getZ() - player.getLocation().getZ() <= 1
                        && e.getClickedBlock().getLocation().getZ() + 0.5 - player.getLocation().getZ() >= -1) {
                    Vector dir = new Vector(0, 0.6, 0);
                    player.setVelocity(dir);
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHAIN_FALL, 1, 1);
                    }
                }
            }
        }
    }
}
