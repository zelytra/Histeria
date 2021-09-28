/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.managers.cooldown.Cooldown;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class UnclaimFinder implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.UNCLAIM_FINDER;
    private final int itemCooldown = 5;
    private final int range = 4; //en chunk

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
                if (!Cooldown.cooldownCheck(player, customMaterial.getName())) {
                    return;
                }
                new Cooldown(player, itemCooldown, customMaterial.getName());
                Chunk chunk = player.getLocation().getChunk();
                int count = 0;
                for (int x = (chunk.getX() - range); x <= (chunk.getX() + range); x++) {
                    for (int z = (chunk.getZ() - range); z <= (chunk.getZ() + range); z++) {
                        BlockState[] entities = player.getWorld().getChunkAt(x, z).getTileEntities();
                        if (entities.length == 0 || !player.getWorld().getChunkAt(x, z).isLoaded()) {
                            continue;
                        }

                        for (BlockState blockState : entities) {
                            if (blockState.getBlock().getType() == Material.CHEST || blockState.getBlock().getType() == Material.TRAPPED_CHEST || blockState.getBlock().getType() == Material.BARREL || blockState.getBlock().getType() == Material.ENDER_CHEST) {
                                count++;
                                player.setCompassTarget(blockState.getLocation());
                            }

                        }

                    }

                }
                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§eThere is " + count + " containers around you.");
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(e.getPlayer().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                }
            }
        }
    }
}
