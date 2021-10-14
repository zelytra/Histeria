package fr.zelytra.histeria.managers.market.stand;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Stand {

    private static List<Stand> serverStands = new ArrayList<>();

    private ItemStack item;
    private int price;
    private Location location;

    public Stand(Location location, int price, ItemStack item) {
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        this.location = location;
        this.price = price;
        this.item = item;
        spawnStand();
    }

    private void spawnStand() {



    }


}
