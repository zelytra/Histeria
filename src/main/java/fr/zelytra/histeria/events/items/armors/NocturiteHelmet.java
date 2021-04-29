package fr.zelytra.histeria.events.items.armors;

import fr.zelytra.histeria.events.items.armors.handler.ArmorEquipEvent;
import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NocturiteHelmet implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.NOCTURITE_HELMET;

    @EventHandler
    public void armorMoveEvent(ArmorEquipEvent e) {
        Player player = e.getPlayer();
        if (CustomItemStack.hasTag(e.getNewArmorPiece(), customMaterial)) {
            PotionEffect potion1 = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999,0, false, false, true);
            player.addPotionEffect(potion1);
            potion1 = new PotionEffect(PotionEffectType.WATER_BREATHING, 999999,1, false, false, true);
            player.addPotionEffect(potion1);

        } else if (CustomItemStack.hasTag(e.getOldArmorPiece(), customMaterial)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        }
    }

    @EventHandler
    public void armorDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        ItemStack armor = player.getInventory().getHelmet();
        if (armor == null || e.isCancelled() | !CustomItemStack.hasTag(armor)) {
            return;
        }
        DurabilityHandler durability = new DurabilityHandler(player, customMaterial, SlotEnum.getArmorSlot(armor));
        durability.iterate();
        if (durability.isBroken()) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        }
    }


}
