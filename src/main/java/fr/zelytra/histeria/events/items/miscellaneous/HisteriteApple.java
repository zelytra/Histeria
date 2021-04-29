package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HisteriteApple implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HISTERITE_APPLE;


    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer()) || CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer())) {
            Player player = e.getPlayer();
            e.setCancelled(true);
            PotionEffect potion1 = new PotionEffect(PotionEffectType.ABSORPTION, 3600, 1, false, false, true);
            PotionEffect potion2 = new PotionEffect(PotionEffectType.REGENERATION, 100, 3, false, false, true);
            player.addPotionEffect(potion1);
            player.addPotionEffect(potion2);
            player.setFoodLevel((int) (player.getFoodLevel() + (20.0 - player.getFoodLevel())));
            player.setSaturation((int) (player.getSaturation() + (20.0 - player.getSaturation())));
            ItemFunction.removeHeldItem(player, customMaterial);
        }
    }
}
