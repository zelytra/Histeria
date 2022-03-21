/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.kit;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.kit.Kit;
import fr.zelytra.histeria.managers.kit.KitEnum;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.utils.Message;
import fr.zelytra.histeria.utils.Utils;
import fr.zelytra.histeria.utils.timer.TimeFormater;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class KitCommand implements CommandExecutor, Listener {

    private final String guiName = "§9Kit";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        openMenu(player);

        return true;
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI) {
            if (e.getView().getTitle().equals(guiName)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case IRON_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.DEFAULT);
                            else
                                giveKit((Player) e.getWhoClicked(), KitEnum.DEFAULT);
                            break;
                        case GOLD_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.VOTE);
                            else
                                giveKit((Player) e.getWhoClicked(), KitEnum.VOTE);
                            break;
                        case DIAMOND_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.LORD);
                            else
                                giveKit((Player) e.getWhoClicked(), KitEnum.LORD);
                            break;
                        case PURPUR_BLOCK:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.MONARCH);
                            else
                                giveKit((Player) e.getWhoClicked(), KitEnum.MONARCH);
                            break;
                        case PURPUR_PILLAR:
                            if (e.getClick() == ClickType.RIGHT)
                                openKitDisplay((Player) e.getWhoClicked(), KitEnum.DEMIGOD);
                            else
                                giveKit((Player) e.getWhoClicked(), KitEnum.DEMIGOD);
                            break;
                        case BARRIER:
                            openMenu((Player) e.getWhoClicked());
                            break;
                    }
                }
            }
        }
    }

    private void openMenu(@NotNull Player player) {
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(9, guiName);
        interfaceBuilder.setContent(getMenu(Objects.requireNonNull(CustomPlayer.getCustomPlayer(player.getName()))));
        interfaceBuilder.open(player);
    }

    private void openKitDisplay(Player player, KitEnum kitEnum) {
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(54, guiName);
        interfaceBuilder.setContent(getKitDisplay(new Kit(kitEnum)));
        interfaceBuilder.open(player);
    }

    private ItemStack @NotNull [] getMenu(@NotNull CustomPlayer player) {
        ItemStack[] content = new ItemStack[9];
        content[0] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[1] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[7] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[8] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[2] = new VisualItemStack(Material.IRON_BLOCK, "§7Histerien", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[3] = new VisualItemStack(Material.GOLD_BLOCK, "§aVote", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[4] = new VisualItemStack(Material.DIAMOND_BLOCK, "§bLord", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[5] = new VisualItemStack(Material.PURPUR_BLOCK, "§cMonarch", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();
        content[6] = new VisualItemStack(Material.PURPUR_PILLAR, "§5Demigod", false, player.getLang().get("kit.claimKit"), "", player.getLang().get("kit.showKit")).getItem();

        return content;
    }

    private ItemStack @NotNull [] getKitDisplay(Kit kit) {

        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x <= 9; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        for (int x = 20; x <= 26; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        for (int x = 45; x <= 52; x++)
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[11] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[18] = VisualType.BLANK_BLUE_GLASSE.getItem();

        content[27] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[29] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[35] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[36] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[38] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[44] = VisualType.BLANK_BLUE_GLASSE.getItem();
        content[53] = new VisualItemStack(Material.BARRIER, "§cBack", false).getItem();

        List<ItemStack> items = kit.getItemList();

        if (items.size() >= 4) {
            content[10] = items.get(0);
            content[19] = items.get(1);
            content[28] = items.get(2);
            content[37] = items.get(3);
        }

        int count = 4;
        for (int x = 12; x <= 16; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        count = 9;
        for (int x = 30; x <= 34; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        count = 14;
        for (int x = 39; x <= 43; x++) {
            if (count < items.size()) {
                content[x] = items.get(count);
                count++;
            }
        }

        return content;

    }

    private void giveKit(Player player, KitEnum kitEnum) {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            if (!(Utils.canByPass(player) || player.hasPermission("group." + kitEnum.getGroupName()))) {
                LangMessage.sendMessage(player, "kit.noPermission");
                return;
            }

            int time = getClaimDelta(player, kitEnum);
            if (!Utils.canByPass(player) && time < kitEnum.getCoolDown()) {
                LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "kit.coolDown", TimeFormater.display(kitEnum.getCoolDown() - time));
                return;
            }

            if (Utils.getEmptySlots(player) <= new Kit(kitEnum).getItemList().size()) {
                LangMessage.sendMessage(player, "miscellaneous.notEnoughSpace");
                return;
            }

            Bukkit.getScheduler().runTask(Histeria.getInstance(), () -> {
                LangMessage.sendMessage(player, "kit.claimKitText");
                for (ItemStack item : new Kit(kitEnum).getItemList()) {
                    player.getInventory().addItem(item);
                }
            });
        });

    }

    private int getClaimDelta(@NotNull Player player, @NotNull KitEnum kitEnum) {
        MySQL mySQL = Histeria.mySQL;
        ResultSet result = mySQL.query("SELECT * FROM `Kit` WHERE `uuid` = '" + player.getUniqueId() + "' AND `kit` = '" + kitEnum.getGroupName() + "';");
        try {
            if (!result.next()) {
                mySQL.update("INSERT INTO `Kit` (`uuid`,`kit`,`cooldown`) VALUE ('" + player.getUniqueId() + "','" + kitEnum.getGroupName() + "'," + System.currentTimeMillis() + ");");
                result.close();
                return kitEnum.getCoolDown() + 1;
            } else {
                long time = result.getLong("cooldown");
                if (((System.currentTimeMillis() - time) / 1000) >= kitEnum.getCoolDown()) {
                    mySQL.update("UPDATE `Kit` SET `cooldown` = " + System.currentTimeMillis() + " WHERE `uuid` = '" + player.getUniqueId() + "' AND `kit` = '" + kitEnum.getGroupName() + "';");
                }

                result.close();
                return (int) ((System.currentTimeMillis() - time) / 1000);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
