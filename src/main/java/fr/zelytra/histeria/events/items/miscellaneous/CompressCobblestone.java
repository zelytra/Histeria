/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.miscellaneous;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.itemHandler.ItemFunction;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.loottable.LootsEnum;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CompressCobblestone implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.COMPRESS_COBBLESTONE;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == CustomMaterial.CRUSHING_TABLE.getVanillaMaterial()) {
                if ((e.getHand() == EquipmentSlot.HAND && CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) || (e.getHand() == EquipmentSlot.OFF_HAND && CustomItemStack.hasCustomItemInOffHand(customMaterial.getName(), e.getPlayer()))) {
                    Player player = e.getPlayer();
                    CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
                    Bukkit.getPluginManager().callEvent(customItemEvent);

                    if(customItemEvent.isCancelled()){
                        return;
                    }
                    int random = (int) (Math.random() * (100 - 1) + 1);
                    for (LootsEnum loots : Histeria.lootTableManager.getCCrusher().getLoots()) {
                        if (random < loots.getLuck()) {
                            player.getInventory().addItem(loots.getItem());
                            if (CustomItemStack.hasTag(loots.getItem())) {
                                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aWell done you found some §l" + loots.getItem().getItemMeta().getDisplayName() + " x" + loots.getItem().getAmount());
                            }
                            else {
                                player.sendMessage(Message.PLAYER_PREFIX.getMsg() + "§aWell done you found some §l" + loots.getItem().getType().toString().toLowerCase().replace("_"," ") + " x" + loots.getItem().getAmount());
                            }
                            break;
                        }
                        random -= loots.getLuck();
                    }

                    Location BLocation = e.getClickedBlock().getLocation();
                    BLocation.setY(BLocation.getY() + 1);
                    BLocation.setZ(BLocation.getZ() + 0.5);
                    BLocation.setX(BLocation.getX() + 0.5);
                    player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, BLocation.getX(), BLocation.getY(), BLocation.getZ(), 0, 0, 0.1, 0);

                    ItemFunction.removeHeldItem(player, customMaterial);
                }
            }
        }
    }
}
