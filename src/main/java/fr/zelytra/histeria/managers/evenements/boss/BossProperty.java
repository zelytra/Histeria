/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.boss;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BossProperty {

    private final BossBar bossBar;
    private final List<PlayerDamager> damagerList = new ArrayList<>();

    private int listenerId;
    private final int schedulerTime = 1; //time in seconds

    public BossProperty(String name) {
        bossBar = Bukkit.createBossBar(new NamespacedKey(Histeria.getInstance(), "bossBar_" + UUID.randomUUID()), name, BarColor.BLUE, BarStyle.SEGMENTED_6, BarFlag.DARKEN_SKY);
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void updateBossBar(Boss boss) {
        bossBar.setProgress((boss.getEntity().getHealth()) / boss.getMaxHealth());
    }

    public void startBossBarListener(Boss boss) {

        listenerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Histeria.getInstance(), () -> {

            if (boss.getEntity() == null) return;

            List<Entity> targets = boss.getEntity().getNearbyEntities(200, 200, 200);

            // Adding players arround the boss to the bossbar
            for (Entity target : targets) {

                if (!(target instanceof Player)) continue;

                if (getDistance(target.getLocation(), boss.getEntity().getLocation()) <= 200)
                    bossBar.addPlayer((Player) target);

                else if (bossBar.getPlayers().contains((Player) target))
                    bossBar.removePlayer((Player) target);

            }

            // Removing offline player from the list
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (targets.contains(p))
                    continue;
                else
                    bossBar.removePlayer(p);

            }

        }, 0, schedulerTime * 20);

    }

    public void killBossBar() {
        Bukkit.getScheduler().cancelTask(listenerId);
        bossBar.removeAll();
    }

    public void damage(Player player, double damage) {
        for (PlayerDamager playerDamager : damagerList) {
            if (playerDamager.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                playerDamager.damage(damage);
                return;
            }
        }

        damagerList.add(new PlayerDamager(player, damage));
    }

    public List<PlayerDamager> getSortedDamager() {
        Collections.sort(damagerList);
        return damagerList;
    }

    public void displayDamageStats() {
        // Display informations
        LangMessage.broadcast("", "boss.title", "");
        List<PlayerDamager> playerDamagers = getSortedDamager();
        for (int x = 0; x <= 9; x++) {
            if (x >= playerDamagers.size()) return;
            Bukkit.broadcastMessage("§8" + (x + 1) + ". §6" + playerDamagers.get(x).getPlayer().getName() + ": §f" + (int) playerDamagers.get(x).getTotalDamage() + "§f " + Emote.HEART);
        }
    }

    public double lifeScaling() {
        double defaultLife = 500.0;
        defaultLife = defaultLife * Bukkit.getOnlinePlayers().size() >= 2048 ? 2047 : defaultLife * Bukkit.getOnlinePlayers().size();
        return defaultLife;
    }

    public void resistanceScaling(LivingEntity entity) {
        double defaultArmor = 3.0;
        defaultArmor = defaultArmor * Bukkit.getOnlinePlayers().size() >= 30 ? 30 : defaultArmor * Bukkit.getOnlinePlayers().size();
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(defaultArmor);
    }

    private double getDistance(Location loc1, Location loc2) {
        double deltaX = loc1.getX() - loc2.getX();
        double deltaZ = (loc1.getZ() - loc2.getZ());
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
    }
}
