package fr.zelytra.histeria.events.items.armors;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.armors.handler.ArmorEquipEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class NocturiteBoots implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.NOCTURITE_BOOTS;

    @EventHandler
    public void armorMoveEvent(ArmorEquipEvent e) {
        Player player = e.getPlayer();
        if (CustomItemStack.hasTag(e.getNewArmorPiece(), customMaterial)) {
            player.setAllowFlight(true);

        } else if (CustomItemStack.hasTag(e.getOldArmorPiece(), customMaterial)) {
            player.setAllowFlight(false);
        }
    }

    @EventHandler
    public void setFlyOnJump(PlayerToggleFlightEvent e) {
        Player jumper = e.getPlayer();
        if (e.isFlying() && jumper.getGameMode() == GameMode.SURVIVAL) {
                jumper.setFlying(false);
                Vector jump = jumper.getLocation().getDirection().multiply(0.2).setY(0.8);
                jumper.setVelocity(jumper.getVelocity().add(jump));
                jumper.setAllowFlight(false);
                runIsNotOnGround(jumper.getUniqueId());
                e.setCancelled(true);
        }
    }

    private void runIsNotOnGround(UUID jumper) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            long time = System.currentTimeMillis();
            while (!Bukkit.getPlayer(jumper).isOnGround()) {
                if (System.currentTimeMillis() - time >= 10000) {
                    Histeria.log("§cNocturiteBoot double jump task time out.");
                    return;
                }
            }
            if (CustomItemStack.hasTag(Bukkit.getPlayer(jumper).getInventory().getBoots(), customMaterial)) {
                Bukkit.getPlayer(jumper).setAllowFlight(true);
            }
        });
    }

}
