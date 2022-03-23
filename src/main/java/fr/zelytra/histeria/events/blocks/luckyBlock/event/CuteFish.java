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
import net.minecraft.world.entity.animal.Pufferfish;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;

public class CuteFish implements LuckyEvent {

    private final int luck;

    public CuteFish(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        CraftWorld world = (CraftWorld) e.getBlock().getWorld();
        Pufferfish fish = new Pufferfish(net.minecraft.world.entity.EntityType.PUFFERFISH, world.getHandle());
        ((LivingEntity) fish.getBukkitEntity()).setCustomName("Give me a hug");
        fish.setPos(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());
        world.getHandle().addFreshEntity(fish);

        e.getPlayer().sendMessage("§dHoooooo so cute... §c§lWAIT WHAT ?!");
    }
}
