/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.evenements.boss;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerDamager implements Comparable<PlayerDamager> {

    private double totalDamage = 0;
    private final Player player;

    public PlayerDamager(Player player) {
        this.player = player;
    }

    public PlayerDamager(Player player, double damage) {
        this.player = player;
        this.totalDamage = damage;
    }

    public void damage(double damage) {
        totalDamage += damage;
    }

    public double getTotalDamage() {
        return totalDamage;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return player.getName() + " | " + totalDamage + " <3";
    }

    @Override
    public int compareTo(@NotNull PlayerDamager o) {
        return ((Double) o.getTotalDamage()).compareTo(getTotalDamage());
    }
}
