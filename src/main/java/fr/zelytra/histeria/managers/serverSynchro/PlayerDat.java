package fr.zelytra.histeria.managers.serverSynchro;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;

public class PlayerDat {
    private final Player player;
    private ItemStack[] inventory;
    private ItemStack[] enderChest;
    private PotionEffect[] effects;
    private double health;
    private int food;
    private int xp;

    public PlayerDat(Player player) {
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
        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() < this.health) {
            player.setHealth((player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
        } else {
            player.setHealth(this.health);
        }


    }
}
