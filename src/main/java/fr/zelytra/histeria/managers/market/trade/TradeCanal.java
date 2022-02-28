/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.trade;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class TradeCanal {

    private final static String guiName = "§6Trade between: §e";

    private final static int[] firstPlayerSlot = new int[]{10, 11, 19, 20, 28, 29, 37, 38};
    private final static int[] secondPlayerSlot = new int[]{15, 16, 24, 25, 33, 34, 42, 43};
    private final static int[] separatorSlot = new int[]{4, 13, 22, 31, 40, 49};
    private final static int firstPlayerValidationSlot = 0, secondPlayerValidationSlot = 8;

    private final Player firstPlayer, secondPlayer;
    private final InterfaceBuilder gui;
    private BukkitTask task;
    int progression = 0; // out of separatorSlot size

    public TradeCanal(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.gui = new InterfaceBuilder(54, firstPlayer.getName() + "§6 | §e" + secondPlayer.getName());
        this.gui.setContent(guiBuilder());
        this.gui.open(firstPlayer);
        this.gui.open(secondPlayer);

    }

    private ItemStack[] guiBuilder() {
        ItemStack[] content = new ItemStack[54];

        // Filling visual glasses
        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        for (int index : separatorSlot)
            content[index] = VisualType.BLANK_ORANGE_GLASSE.getItem();

        // Removing glasses on trade player's slot
        for (int index : firstPlayerSlot)
            content[index] = null;

        for (int index : secondPlayerSlot)
            content[index] = null;

        // Setting confirmation button
        content[firstPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();
        content[secondPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();


        return content;
    }

    public void startTrade() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Histeria.getInstance(), () -> {

            //Trade validate
            if (progression == secondPlayerSlot.length - 1) {
                executeTrade();
            }

            if (progression < 2)
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_RED_GLASSE.getItem());
            if (progression < 4)
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_ORANGE_GLASSE.getItem());
            else
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_GREEN_GLASSE.getItem());

            firstPlayer.playSound(firstPlayer, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
            secondPlayer.playSound(secondPlayer, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
            progression++;
            //TODO progress message

        }, 0, 20);
    }

    private void executeTrade() {
        ItemStack[] content = gui.getInventory().getContents();
        gui.getInventory().close();

        // Giving items to players
        for (int index : firstPlayerSlot) {
            if (content[index] == null) continue;
            secondPlayer.getInventory().addItem(content[index]);
        }

        for (int index : secondPlayerSlot) {
            if (content[index] == null) continue;
            firstPlayer.getInventory().addItem(content[index]);
        }
        //TODO success message
    }

    public void cancelTrade() {
        this.task.cancel();
        resetProgressBar();
        progression = 0;
        //TODO cancel message
    }

    private void resetProgressBar() {
        ItemStack[] content = gui.getInventory().getContents();

        // Resetting
        for (int index : separatorSlot)
            content[index] = VisualType.BLANK_ORANGE_GLASSE.getItem();

        // Setting confirmation button
        content[firstPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();
        content[secondPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();

    }


}
