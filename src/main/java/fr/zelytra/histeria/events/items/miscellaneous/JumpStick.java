package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpStick implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.JUMP_STICK;
    private final int itemCooldown = 15;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {
        if (e.getMaterial() != customMaterial) return;

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

        ItemFunction.removeHeldItem(player, customMaterial);


    }
}
