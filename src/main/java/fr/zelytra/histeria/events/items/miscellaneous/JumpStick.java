package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.ItemFunction;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.JUMP_STICK;
    private final int itemCooldown = 15;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                Player player = e.getPlayer();

                //Cooldown check
                if (!Cooldown.cooldownCheck(player, customMaterial.getName())) {
                    return;
                }
                new Cooldown(player, itemCooldown, customMaterial.getName());

                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 2, false, false, true));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
                }

                ItemFunction.removeHeldItem(e, customMaterial);
            }
        }
    }
}
