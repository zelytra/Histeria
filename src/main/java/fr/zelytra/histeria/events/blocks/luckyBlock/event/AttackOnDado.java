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
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
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
        CraftWorld world = (CraftWorld) e.getBlock().getWorld();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer("Dadodasyra"));
        item.setItemMeta(skull);

        Zombie customDado = new Zombie(net.minecraft.world.entity.EntityType.ZOMBIE, world.getHandle());
        customDado.setPos(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(),
                e.getBlock().getLocation().getZ());
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setBoots(new CustomItemStack(CustomMaterial.NOCTURITE_BOOTS, 1).getItem());
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setLeggings(new CustomItemStack(CustomMaterial.NOCTURITE_LEGGINGS, 1).getItem());
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setChestplate(new CustomItemStack(CustomMaterial.NOCTURITE_CHESTPLATE, 1).getItem());
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setHelmet(item);
        ((LivingEntity) customDado.getBukkitEntity()).setCustomName("§cdadodasyra");
        ((LivingEntity) customDado.getBukkitEntity()).setCustomNameVisible(true);
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setItemInMainHand(new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem());
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setBootsDropChance(0);
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setLeggingsDropChance(0);
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setChestplateDropChance(0);
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setHelmetDropChance((float) 0.01);
        ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setItemInMainHandDropChance(0);

        world.getHandle().addFreshEntity(customDado);

    }
}
