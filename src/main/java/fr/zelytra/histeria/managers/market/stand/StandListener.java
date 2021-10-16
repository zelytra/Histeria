/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.stand;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class StandListener implements Listener {

    @EventHandler
    public void onStandClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) e.getRightClicked();
            if (armorStand.getPersistentDataContainer().has(Stand.shopKey, PersistentDataType.STRING)) {

                PersistentDataContainer dataContainer = armorStand.getPersistentDataContainer();
                Stand stand = Stand.getStand(dataContainer.get(Stand.shopKey, PersistentDataType.STRING));
                stand.openInterface(e.getPlayer());

            }
        }
    }

    @EventHandler
    public void onItemDispawn(ItemDespawnEvent e) {
        if (e.getEntity().getPersistentDataContainer().has(Stand.shopKey, PersistentDataType.STRING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onShopClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI) {
            Player player = (Player) e.getWhoClicked();
            if (e.getView().getTitle().equals(Stand.shopName)) {
                if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    switch (e.getCurrentItem().getType()) {
                        case SLIME_BALL:
                            Stand stand = Stand.getStand(e.getInventory().getItem(13));
                            CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());

                            if (customPlayer.getBankAccount().getMoney() < stand.getPrice()) {
                                LangMessage.sendMessage(player, "shop.notEnoughMoney");
                            } else {
                                customPlayer.getBankAccount().takeMoney(stand.getPrice());
                                player.getInventory().addItem(stand.getItem());
                                LangMessage.sendMessage(player, Message.PLAYER_PREFIX.getMsg(), "shop.buyItem", "§6" + stand.getItemName() + " §a-> §6" + stand.getPrice() + " §f" + Emote.GOLD);
                                e.getInventory().close();
                            }
                            break;
                        case BARRIER:
                            e.getInventory().close();
                            break;
                    }
                }
            }
        }
    }
}
