package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChestExplorer implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.CHEST_EXPLORER;


    @EventHandler
    public void onInteract(CustomItemUseEvent e) {
        if (e.getMaterial() != customMaterial) return;
        Player player = e.getPlayer();

        if (e.getEvent().getClickedBlock().getType() == Material.CHEST || e.getEvent().getClickedBlock().getType() == Material.TRAPPED_CHEST) {

            Chest chest = (Chest) e.getEvent().getClickedBlock().getState();
            player.openInventory(chest.getInventory());

        } else if (e.getEvent().getClickedBlock().getType() == Material.BARREL) {

            Barrel barrel = (Barrel) e.getEvent().getClickedBlock().getState();
            player.openInventory(barrel.getInventory());

        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(e.getPlayer().getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
        }

        ItemFunction.removeHeldItem(player, customMaterial);
    }


}
