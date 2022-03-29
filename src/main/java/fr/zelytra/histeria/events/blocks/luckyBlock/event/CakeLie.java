/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.event;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CakeLie implements LuckyEvent {

    private final int luck;

    public CakeLie(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.setCancelled(true);
        e.getBlock().setType(Material.CAKE);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2, false, false, true));
        Location fLocation = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX() + 0.5, e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ() + 0.5);
        Firework fw = (Firework) e.getBlock().getWorld().spawnEntity(fLocation, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(20);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
        fw = (Firework) e.getBlock().getWorld().spawnEntity(fLocation, EntityType.FIREWORK);
        fwm = fw.getFireworkMeta();

        fwm.setPower(20);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.BLUE).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
        Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> e.getPlayer().sendMessage("§9[§6GLaDOS§9] §fOh... it's not a deer."), 25);


    }
}
