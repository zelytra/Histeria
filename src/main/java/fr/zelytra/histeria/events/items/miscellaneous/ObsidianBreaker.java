package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ObsidianBreaker implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.OBSIDIAN_BREAKER;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {
        if (e.getMaterial() != customMaterial) return;

        switch (e.getEvent().getClickedBlock().getType()) {
            case OBSIDIAN:
                breakBlockFX(e.getEvent());
                break;
            case LODESTONE:
                if ((Math.floor(Math.random() * 8) + 1) == 1) {
                    breakBlockFX(e.getEvent());
                } else {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.playSound(e.getEvent().getClickedBlock().getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    }
                }
                break;
            default:
                return;
        }
        ItemFunction.removeHeldItem(e.getPlayer(), customMaterial);
    }


    private void breakBlockFX(PlayerInteractEvent e) {
        e.getClickedBlock().breakNaturally(new ItemStack(Material.AIR));
        e.getPlayer().getWorld().spawnParticle(Particle.LAVA, e.getClickedBlock().getLocation(), 10);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
        }
    }
}
