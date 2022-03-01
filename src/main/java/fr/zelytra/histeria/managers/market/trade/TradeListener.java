package fr.zelytra.histeria.managers.market.trade;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class TradeListener implements Listener {

    private static final List<InventoryAction> authorizeActions = new ArrayList<>();

    {
        authorizeActions.add(InventoryAction.PICKUP_ONE);
        authorizeActions.add(InventoryAction.PICKUP_ALL);
        authorizeActions.add(InventoryAction.PICKUP_SOME);
        authorizeActions.add(InventoryAction.PICKUP_HALF);
        authorizeActions.add(InventoryAction.PLACE_ALL);
        authorizeActions.add(InventoryAction.PLACE_ONE);
        authorizeActions.add(InventoryAction.PLACE_SOME);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;

        Player player = (Player) e.getWhoClicked();
        Inventory clickedInventory = e.getClickedInventory();

        TradeCanal tradeCanal = TradeCanal.getByPlayerName(e.getWhoClicked().getName());
        if (tradeCanal == null) return;

        if (tradeCanal.isTrading()) {
            tradeCanal.cancelTrade();
        }

        // Blocking slot to respective player
        if (clickedInventory != null && clickedInventory.getType() == InventoryType.CHEST) {
            if (!tradeCanal.canInteractWith(e.getSlot(), player)) e.setCancelled(true);
        }

        // Blocking wrong action
        if (clickedInventory != null && clickedInventory.getType() != InventoryType.CHEST) {
            if (!authorizeActions.contains(e.getAction())) e.setCancelled(true);
        }

        // Updating validation/cancel trade item validation
        if (e.getCurrentItem() != null && (e.getCurrentItem().getType() == Material.BARRIER || e.getCurrentItem().getType() == VisualType.VALIDAY.getType())) {
            tradeCanal.validationClick(e.getCurrentItem(), player, e.getSlot());
        }

        if (tradeCanal.canTrade())
            tradeCanal.startTrade();

    }

    @EventHandler
    public void onClick(InventoryDragEvent e) {
        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;


        TradeCanal tradeCanal = TradeCanal.getByPlayerName(e.getWhoClicked().getName());
        if (tradeCanal == null) return;

        e.setCancelled(true);


    }


    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;

        TradeCanal tradeCanal = TradeCanal.getByPlayerName(e.getPlayer().getName());
        if (tradeCanal == null) return;

        if (tradeCanal.isTradeSuccess())
            return;

        //Cloning viewer list
        List<HumanEntity> cloneEntity = new ArrayList<>();
        cloneEntity.addAll(e.getViewers());

        tradeCanal.close(cloneEntity);

    }
}
