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
import org.bukkit.Sound;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BadPotions implements LuckyEvent {

    private final int luck;

    public BadPotions(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    @Override
    public void run(BlockBreakEvent e) {
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 10, 1);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30, 0, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 1, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 600000, 3, true, true, true));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 700, 1, true, true, true));


    }
}
