package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HealStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HEAL_STICK;
    private final int itemCooldown = 2;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {
        if (e.getMaterial() != customMaterial) return;

        Player player = e.getPlayer();

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


