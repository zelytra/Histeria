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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TradeCanal {

    private final static String guiName = "§6Trade : §e";
    private final static List<TradeCanal> canal = new ArrayList<>();

    public final static int[] firstPlayerSlot = new int[]{10, 11, 19, 20, 28, 29, 37, 38};
    public final static int[] secondPlayerSlot = new int[]{15, 16, 24, 25, 33, 34, 42, 43};
    private final static int[] separatorSlot = new int[]{4, 13, 22, 31, 40, 49};
    public final static int firstPlayerValidationSlot = 0, secondPlayerValidationSlot = 8;

    private final Player firstPlayer, secondPlayer;
    private final InterfaceBuilder gui;
    private BukkitTask task;
    private boolean isTrading = false, tradeSuccess = false;
    int progression = 0; // out of separatorSlot size

    public TradeCanal(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.gui = new InterfaceBuilder(54, guiName + firstPlayer.getName() + "§6 | §e" + secondPlayer.getName());
        this.gui.setContent(guiBuilder());
        this.gui.open(firstPlayer);
        this.gui.open(secondPlayer);
        canal.add(this);

    }

    private ItemStack[] guiBuilder() {
        ItemStack[] content = new ItemStack[54];

        // Filling visual glasses
        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        for (int index : separatorSlot)
            content[index] = VisualType.BLANK_GRAY_GLASSE.getItem();

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
        isTrading = true;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Histeria.getInstance(), () -> {

            //Trade validate
            if (progression == separatorSlot.length - 1) {
                Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> executeTrade());
                firstPlayer.playSound(firstPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 2f);
                secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 2f);
                tradeSuccess = true;
                task.cancel();
            }

            if (progression > 4) {
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_GREEN_GLASSE.getItem());
                firstPlayer.playSound(firstPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1.5f);
                secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1.5f);
            } else if (progression > 2) {
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_ORANGE_GLASSE.getItem());
                firstPlayer.playSound(firstPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1f);
                secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1f);
            } else {
                gui.getInventory().setItem(separatorSlot[progression], VisualType.BLANK_RED_GLASSE.getItem());
                firstPlayer.playSound(firstPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                secondPlayer.playSound(secondPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
            }

            progression++;

        }, 0, 20);
    }

    private void executeTrade() {
        ItemStack[] content = gui.getInventory().getContents();
        gui.getInventory().close();
        destroy();

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
        task.cancel();
        isTrading = false;
        progression = 0;
        resetProgressBar();
        //TODO cancel message
    }

    public void destroy() {
        canal.remove(this);
    }

    public boolean isTrading() {
        return isTrading;
    }

    public boolean isTradeSuccess() {
        return tradeSuccess;
    }

    public boolean canInteractWith(int slot, Player player) {

        if (firstPlayer.getName().equalsIgnoreCase(player.getName()))
            return containInt(firstPlayerSlot, slot);

        if (secondPlayer.getName().equalsIgnoreCase(player.getName()))
            return containInt(secondPlayerSlot, slot);

        else return false;

    }

    public static TradeCanal getByPlayerName(String name) {
        for (TradeCanal tradeCanal : canal)
            if (tradeCanal.firstPlayer.getName().equalsIgnoreCase(name) || tradeCanal.secondPlayer.getName().equalsIgnoreCase(name))
                return tradeCanal;
        return null;
    }

    private void resetProgressBar() {
        ItemStack[] content = gui.getInventory().getContents();

        // Resetting
        for (int index : separatorSlot)
            content[index] = VisualType.BLANK_GRAY_GLASSE.getItem();

        // Setting confirmation button
        content[firstPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();
        content[secondPlayerValidationSlot] = new VisualItemStack(CustomMaterial.VALIDAY, "§aClick here to confirm your trade", false).getItem();

        gui.setContent(content);

    }


    public void close(List<HumanEntity> viewers) {
        ItemStack[] content = gui.getInventory().getContents().clone();

        // Overflow close inventory calling security
        if (!canal.contains(this)) return;
        destroy();
        gui.getInventory().close();

        for (HumanEntity human : viewers) {

            // Giving items to players
            if (firstPlayer.getName().equalsIgnoreCase(human.getName())) {
                for (int index : firstPlayerSlot) {
                    if (content[index] == null) continue;
                    firstPlayer.getInventory().addItem(content[index]);
                }
            }
            if (secondPlayer.getName().equalsIgnoreCase(human.getName())) {
                for (int index : secondPlayerSlot) {
                    if (content[index] == null) continue;
                    secondPlayer.getInventory().addItem(content[index]);
                }
            }

        }
    }

    private boolean containInt(int[] values, int x) {
        for (int value : values)
            if (value == x) return true;
        return false;
    }

    public void validationClick(ItemStack item, Player player, int slot) {
        if (firstPlayer.getName().equalsIgnoreCase(player.getName())) {
            if (slot != firstPlayerValidationSlot) return;
            gui.getInventory().setItem(slot, item.getType() == Material.BARRIER ? VisualType.VALIDAY.getItem() : new VisualItemStack(Material.BARRIER, "§cClick here to cancel the trade", false).getItem());
        }
        if (secondPlayer.getName().equalsIgnoreCase(player.getName())) {
            if (slot != secondPlayerValidationSlot) return;
            gui.getInventory().setItem(slot, item.getType() == Material.BARRIER ? VisualType.VALIDAY.getItem() : new VisualItemStack(Material.BARRIER, "§cClick here to cancel the trade", false).getItem());
        }

    }

    public boolean canTrade() {
        if (gui.getInventory().getItem(firstPlayerValidationSlot).getType() == Material.BARRIER &&
                gui.getInventory().getItem(secondPlayerValidationSlot).getType() == Material.BARRIER)
            return true;
        return false;
    }
}
