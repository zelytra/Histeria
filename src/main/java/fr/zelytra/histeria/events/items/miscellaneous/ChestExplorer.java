package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ChestExplorer implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.CHEST_EXPLORER;


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                Player player = e.getPlayer();

                if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    player.openInventory(chest.getInventory());
                }
                else if(e.getClickedBlock().getType() == Material.BARREL) {
                    Barrel barrel = (Barrel) e.getClickedBlock().getState();
                    player.openInventory(barrel.getInventory());
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(e.getPlayer().getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
                }

                ItemFunction.removeHeldItem(e, customMaterial);
            }
        }
    }
}
