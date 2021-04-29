package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ObsidianBreaker implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.OBSIDIAN_BREAKER;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {

                switch (e.getClickedBlock().getType()) {
                    case OBSIDIAN:
                        breakBlockFX(e);
                        break;
                    case LODESTONE:
                        if ((Math.floor(Math.random() * 8) + 1) == 1) {
                            breakBlockFX(e);
                        }
                        else {
                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                pl.playSound(e.getClickedBlock().getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                            }
                        }
                        break;
                    default:
                        return;
                }
                ItemFunction.removeHeldItem(e.getPlayer(), customMaterial);
            }
        }
    }

    private void breakBlockFX(PlayerInteractEvent e) {
        e.getClickedBlock().breakNaturally(new ItemStack(Material.AIR));
        e.getPlayer().getWorld().spawnParticle(Particle.LAVA, e.getClickedBlock().getLocation(), 10);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
        }
    }
}
