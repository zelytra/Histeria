/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemUseEvent;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BubbleWand implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.BUBBLE_WAND;
    private final int range = 3;

    @EventHandler
    public void onInteract(CustomItemUseEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if(e.isCancelled()) return;

        Player player = e.getPlayer();

        Location LBlock = new Location(player.getWorld(), player.getLocation().getX() - range, player.getLocation().getY() - range, player.getLocation().getZ() - range);
        int nbr = 0;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    LBlock.setX(player.getLocation().getX() + x);
                    LBlock.setY(player.getLocation().getY() + y);
                    LBlock.setZ(player.getLocation().getZ() + z);
                    if (LBlock.getBlock().getType().equals(Material.WATER) || LBlock.getBlock().getType().equals(Material.LAVA)) {
                        LBlock.getBlock().setType(Material.AIR, false);
                        e.getPlayer().getWorld().spawnParticle(Particle.BUBBLE_POP, LBlock, 1);
                        nbr++;
                    }
                }
            }
        }

        if (nbr == 0) {
            player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cNo liquid found around you.");
            return;
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.playSound(e.getPlayer().getLocation(), Sound.ENTITY_DOLPHIN_SPLASH, 1, 1);
        }

        ItemFunction.removeHeldItem(player, customMaterial);


    }
}
