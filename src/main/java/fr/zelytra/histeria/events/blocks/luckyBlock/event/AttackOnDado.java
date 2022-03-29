/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class AttackOnDado implements LuckyEvent {

    private final int luck;

    public AttackOnDado(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    //TODO Remove useless NMS usages
    @Override
    public void run(BlockBreakEvent e) {

        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer("Dadodasyra"));
        item.setItemMeta(skull);

        LivingEntity dado = (Zombie) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.ZOMBIE);
        dado.setCustomName("§cdadodasyra");
        dado.setCustomNameVisible(true);
        dado.getEquipment().setBoots(new CustomItemStack(CustomMaterial.NOCTURITE_BOOTS, 1).getItem());
        dado.getEquipment().setLeggings(new CustomItemStack(CustomMaterial.NOCTURITE_LEGGINGS, 1).getItem());
        dado.getEquipment().setChestplate(new CustomItemStack(CustomMaterial.NOCTURITE_CHESTPLATE, 1).getItem());
        dado.getEquipment().setHelmet(item);
        dado.getEquipment().setItemInMainHand(new CustomItemStack(CustomMaterial.ANTI_NOCTURITE_SWORD, 1).getItem());
        dado.getEquipment().setBootsDropChance(0);
        dado.getEquipment().setLeggingsDropChance(0);
        dado.getEquipment().setChestplateDropChance(0);
        dado.getEquipment().setHelmetDropChance((float) 0.01);
        dado.getEquipment().setItemInMainHandDropChance(0);

    }
}
