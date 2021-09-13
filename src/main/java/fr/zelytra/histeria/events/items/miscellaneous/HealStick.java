package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HealStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HEAL_STICK;
    private final int itemCooldown = 2;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                Player player = e.getPlayer();
                CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
                Bukkit.getPluginManager().callEvent(customItemEvent);

                if(customItemEvent.isCancelled()){
                    return;
                }
                //Cooldown check
                if (player.getHealth() == player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                    player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§6Your health is already full.");
                    return;
                }
                if (!Cooldown.cooldownCheck(player, customMaterial.getName())) {
                    return;
                }
                new Cooldown(player, itemCooldown, customMaterial.getName());

                double remainHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - player.getHealth();
                if (remainHealth < 8.0) {
                    player.setHealth(player.getHealth() + remainHealth);
                } else {
                    player.setHealth(player.getHealth() + 8.0);
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
                }
                ItemFunction.removeHeldItem(player, customMaterial);
            }
        }
    }
}
