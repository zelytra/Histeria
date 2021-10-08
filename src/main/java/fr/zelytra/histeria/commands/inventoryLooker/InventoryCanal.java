/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.inventoryLooker;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class InventoryCanal {

    public static List<InventoryCanal> inventoryCanals = new ArrayList<>();

    private InterfaceBuilder inventory;
    private Player target;
    private Player viewer;
    private BukkitTask task = null;

    public InventoryCanal(Player target, Player viewer) {

        this.inventory = new InterfaceBuilder(54, "§6" + viewer.getName() + "'s inventory");
        this.target = target;
        this.viewer = viewer;
        inventoryCanals.add(this);


        this.inventory.setContent(viewerInvBuilder());
        this.inventory.open(viewer);

        this.task = Bukkit.getScheduler().runTaskTimer(Histeria.getInstance(), () -> {

            boolean isViewerWatching = false;
            for (HumanEntity entity : this.inventory.getInventory().getViewers()) {
                if (entity instanceof Player)
                    isViewerWatching = entity.getName() == viewer.getName();
            }

            if (!isViewerWatching) {
                this.task.cancel();
                inventoryCanals.remove(this);
            }
        }, 0, 5);

    }

    public InterfaceBuilder getInventory() {
        return inventory;
    }

    public Player getTarget() {
        return target;
    }

    public Player getViewer() {
        return viewer;
    }

    public ItemStack[] viewerInvBuilder() {
        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x <= 8; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        for (int x = 36; x <= 44; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        //OffHand
        content[6] = target.getInventory().getItemInOffHand();

        //Armor
        int count = 1;
        for (ItemStack armor : target.getInventory().getArmorContents()) {
            content[count] = armor;
            count++;
        }

        //Content
        for (int x = 9; x <= 35; x++)
            content[x] = target.getInventory().getContents()[x];

        //Hot bar
        count = 45;
        for (int x = 0; x <= 8; x++) {
            content[count] = target.getInventory().getContents()[x];
            count++;
        }

        return content;
    }

    public void targetInvBuilder() {

        ItemStack[] content = this.inventory.getInventory().getContents();
        ItemStack[] armorContent = new ItemStack[4];
        ItemStack[] invContent = new ItemStack[41];

        int count = 0;
        for (int x = 1; x <= 4; x++) {
            armorContent[count] = content[x];
            count++;
        }

        System.arraycopy(content, 9, invContent, 9, 27);

        count = 45;
        for (int x = 0; x <= 8; x++) {
            invContent[x] = content[count];
            count++;
        }

        PlayerInventory inventory = this.target.getInventory();

        inventory.setContents(invContent);
        inventory.setItemInOffHand(content[6]);
        inventory.setArmorContents(armorContent);

        this.target.updateInventory();
    }


}
