package fr.zelytra.histeria.builder.guiBuilder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InterfaceBuilder {

    private Inventory inventory;

    /**
     *
     * @param size Size of the interface
     * @param name Name of the interface
     */

    public InterfaceBuilder(int size, String name) {
        this.inventory = Bukkit.createInventory(new CustomGUI(), size, name);
    }

    public void setContent(ItemStack[] content) {
        this.inventory.setContents(content);
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }


}
