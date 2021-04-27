package fr.zelytra.histeria.builder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceBuilder {

    private Inventory inventory;
    private Player player;

    public InterfaceBuilder(int size, String name, Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(new CustomGUI(), size, name);
    }

    public void setContent(ItemStack[] content) {
        this.inventory.setContents(content);
    }

    public void open() {
        this.player.openInventory(this.inventory);
    }


}
