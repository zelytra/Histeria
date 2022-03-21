/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.armors.handler;

import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.items.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ArmorsHandler implements Listener {

    @EventHandler
    public void armorMoveEvent(ArmorEquipEvent e) {
        Player player = e.getPlayer();
        CustomMaterial material;

        if (CustomItemStack.hasTag(e.getNewArmorPiece())) {
            material = CustomItemStack.getCustomMaterial(e.getNewArmorPiece());
            if (material.getItemType() == ItemType.ARMOR)
                for (PotionEffect potion : material.getPotions()) {
                    player.addPotionEffect(potion);
                }

        } else if (CustomItemStack.hasTag(e.getOldArmorPiece())) {
            material = CustomItemStack.getCustomMaterial(e.getOldArmorPiece());
            if (material.getItemType() == ItemType.ARMOR)
                for (PotionEffect potion : material.getPotions()) {
                    player.removePotionEffect(potion.getType());
                }
        }
    }

    List<EntityDamageEvent.DamageCause> blacklistCause = new ArrayList<>();

    {
        blacklistCause.add(EntityDamageEvent.DamageCause.LAVA);
        blacklistCause.add(EntityDamageEvent.DamageCause.POISON);
        blacklistCause.add(EntityDamageEvent.DamageCause.STARVATION);
        blacklistCause.add(EntityDamageEvent.DamageCause.THORNS);
        blacklistCause.add(EntityDamageEvent.DamageCause.MAGIC);
    }

    @EventHandler
    public void armorDamageEvent(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player))
            return;

        Player player = (Player) e.getEntity();
        ItemStack[] armors = player.getInventory().getArmorContents();

        if (blacklistCause.contains(e.getCause())) return;

        for (ItemStack armor : armors) {

            if (armor == null || e.isCancelled() | !CustomItemStack.hasTag(armor))
                continue;

            CustomMaterial material = CustomItemStack.getCustomMaterial(armor);
            if (material.getItemType() != ItemType.ARMOR) continue;

            DurabilityHandler durability = new DurabilityHandler(player, material, SlotEnum.getArmorSlot(armor));
            durability.iterate();

            if (durability.isBroken())
                for (PotionEffect potion : material.getPotions())
                    player.removePotionEffect(potion.getType());


        }
    }
}
