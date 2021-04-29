package fr.zelytra.histeria.events.items.armors;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.armors.handler.ArmorEquipEvent;
import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
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
            if (CustomItemStack.hasTag(jumper.getInventory().getBoots(), customMaterial)) {
                jumper.setFlying(false);
                Vector jump = jumper.getLocation().getDirection().multiply(0.2).setY(0.8);
                jumper.setVelocity(jumper.getVelocity().add(jump));
                jumper.setAllowFlight(false);
                runIsNotOnGround(jumper.getUniqueId());
                e.setCancelled(true);
            }
        }
    }

    private void runIsNotOnGround(UUID jumper) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            while (!Bukkit.getPlayer(jumper).isOnGround()) ;
            Bukkit.getPlayer(jumper).setAllowFlight(true);
        });
    }

    @EventHandler
    public void armorDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        ItemStack armor = player.getInventory().getChestplate();
        if (armor == null || e.isCancelled() | !CustomItemStack.hasTag(armor)) {
            return;
        }
        DurabilityHandler durability = new DurabilityHandler(player, customMaterial, SlotEnum.getArmorSlot(armor));
        durability.iterate();
    }


}
