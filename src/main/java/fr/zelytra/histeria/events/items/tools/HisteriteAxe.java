package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class HisteriteAxe implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_AXE;
    private final ArrayList<Material> log = new ArrayList<>(Arrays.asList(Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM, Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM));


    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) {
            Player player = e.getPlayer();
            CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
            Bukkit.getPluginManager().callEvent(customItemEvent);

            if(customItemEvent.isCancelled()){
                return;
            }

            Location BLocation = e.getBlock().getLocation();
            DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);
            while (isLogBlock(BLocation.getBlock().getType())) {
                BLocation.getBlock().breakNaturally();
                BLocation.setY(BLocation.getY() + 1);
                durabilityHandler.iterate();
                player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
            }
        }
    }

    private boolean isLogBlock(Material block) {
        for (Material material : log) {
            if (material == block) {
                return true;
            }
        }
        return false;
    }
}
