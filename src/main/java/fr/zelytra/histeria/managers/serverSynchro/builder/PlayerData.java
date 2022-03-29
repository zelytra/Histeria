/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.builder;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.commands.vanish.Vanish;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;

public class PlayerData {

    private final Player player;
    private ItemStack[] inventory;
    private ItemStack[] enderChest;
    private ItemStack cursor;
    private PotionEffect[] effects;
    private double health;
    private int food;
    private int xp;
    private Location teleportTask = null;
    private boolean isVanish = false;
    private GameMode gameMode = GameMode.SURVIVAL;

    public PlayerData(Player player) {
        this.player = player;
    }

    public void setEffects(PotionEffect[] effects) {
        this.effects = effects;
    }

    public void setEnderChest(ItemStack[] enderChest) {
        this.enderChest = enderChest;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setTeleportTask(Location teleportTask) {
        this.teleportTask = teleportTask;
    }

    public void setVanish(boolean vanish) {
        isVanish = vanish;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setCursor(ItemStack cursor) {
        this.cursor = cursor;
    }

    public void buildPlayer() {
        //Set inventory
        player.getInventory().setContents(this.inventory);
        player.updateInventory();

        // Enderchest
        player.getEnderChest().setContents(this.enderChest);

        // Food
        player.setFoodLevel(this.food);

        // Food
        player.setLevel(this.xp);

        // Potion effect
        Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.addPotionEffects(Arrays.asList(this.effects));
        });


        // Health
        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < this.health)
            player.setHealth((player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
        else
            player.setHealth(this.health);

        //GameMode
        if (player.getGameMode() != gameMode)
            Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> player.setGameMode(gameMode));

        //Home task
        if (teleportTask != null)
            Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> player.teleport(teleportTask));

        // Vanish
        if (isVanish)
            Vanish.vanish(player);
        else
            Vanish.unvanish(player);

        //Cursor item
        //player.setItemOnCursor(cursor);


    }

    public void clear() {
        Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
            player.getInventory().clear();
            player.getEnderChest().clear();
            player.getActivePotionEffects().clear();
            player.setHealth((player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
            player.setGameMode(GameMode.SURVIVAL);
        });
    }
}
