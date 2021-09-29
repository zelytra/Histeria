package fr.zelytra.histeria.events.items.tools;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

public class AlpineHook implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.ALPIN_HOOK;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if (e.isCancelled()) return;

        if (e.getEvent().getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = e.getPlayer();

        if (e.getEvent().getClickedBlock().getLocation().getX() - player.getLocation().getX() <= 1
                && e.getEvent().getClickedBlock().getLocation().getX() + 0.5 - player.getLocation().getX() >= -1
                && e.getEvent().getClickedBlock().getLocation().getY() - player.getLocation().getY() <= 2
                && e.getEvent().getClickedBlock().getLocation().getY() - player.getLocation().getY() >= 0
                && e.getEvent().getClickedBlock().getLocation().getZ() - player.getLocation().getZ() <= 1
                && e.getEvent().getClickedBlock().getLocation().getZ() + 0.5 - player.getLocation().getZ() >= -1) {
            Vector dir = new Vector(0, 0.6, 0);
            player.setVelocity(dir);

            for (Player pl : Bukkit.getOnlinePlayers())
                pl.playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHAIN_FALL, 1, 1);

        }
    }
}


