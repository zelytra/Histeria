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
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class Zaucisse implements LuckyEvent {

    private final int luck;

    public Zaucisse(int luck) {
        this.luck = luck;
    }

    @Override
    public double getLuck() {
        return luck;
    }

    private int count = 0; //TODO clean this code

    @Override
    public void run(BlockBreakEvent e) {
        Player player = e.getPlayer();
        for (int x = 0; x <= 150; x++) {
            Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {

                double randomx = (Math.random() * (10.0 + 10.0 + 1)) - 10.0;
                double randomy = (Math.random() * (10.0));
                double randomz = (Math.random() * (10.0 + 10.0 + 1)) - 10.0;

                Location Location1 = new Location(e.getBlock().getWorld(),
                        e.getBlock().getLocation().getX() + randomx,
                        e.getBlock().getLocation().getY() + randomy,
                        e.getBlock().getLocation().getZ() + randomz);

                Firework fw = (Firework) e.getBlock().getWorld().spawnEntity(Location1, EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();

                fwm.setPower(20);
                fwm.addEffect(FireworkEffect.builder().withColor(Color.fromRGB((int) (Math.random() * (16000000) + 1))).flicker(true).build());
                fw.setFireworkMeta(fwm);
                fw.detonate();

                if (count == 1) {
                    player.sendMessage("§dWhat a Ramdam ! Like the French like to say");
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 10) {
                    player.sendMessage("§9§kH§r§bI wish you a very merry Unbirthday !§9§kH");
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 25) {
                    player.sendTitle("§cH§6a§2p§dp§by §cN§6e§2w §dY§9e§ca§6r §9§2!", null, 1, 18, 1);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 45) {
                    player.sendTitle("§a...", null, 1, 18, 1);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 65) {
                    player.sendTitle("§aWait...", null, 1, 18, 1);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 85) {
                    player.sendTitle("§aNo...", null, 1, 18, 1);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                if (count == 105) {
                    player.sendTitle("§aOr maybe yes,", "§anever know when you open this block ^^", 1, 50, 1);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                }
                count++;

            }, x + 10);

        }

    }
}
