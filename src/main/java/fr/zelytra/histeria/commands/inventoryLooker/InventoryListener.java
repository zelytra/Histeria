package fr.zelytra.histeria.commands.inventoryLooker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvInteract(InventoryClickEvent e){
        System.out.println("trigger");
        Player player = (Player) e.getWhoClicked();
        System.out.println(player.getName());
        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals){

            if (player.getName().equalsIgnoreCase(inventoryCanal.getTarget().getName())){
                System.out.println("isTarget");
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();
                inventoryCanal.targetInvBuilder();

            }else if (player.getName().equalsIgnoreCase(inventoryCanal.getViewer().getName())){
                System.out.println("isViewer");
                inventoryCanal.targetInvBuilder();
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();
            }

        }
    }

    @EventHandler
    public void onInvInteract(InventoryDragEvent e){
        System.out.println("trigger");
        Player player = (Player) e.getWhoClicked();
        System.out.println(player.getName());
        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals){

            if (player.getName().equalsIgnoreCase(inventoryCanal.getTarget().getName())){
                System.out.println("isTarget");
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();
                inventoryCanal.targetInvBuilder();

            }else if (player.getName().equalsIgnoreCase(inventoryCanal.getViewer().getName())){
                System.out.println("isViewer");
                inventoryCanal.targetInvBuilder();
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();

            }

        }
    }

    @EventHandler
    public void onInvInteract(PlayerSwapHandItemsEvent e){
        System.out.println("trigger");
        Player player = e.getPlayer();
        System.out.println(player.getName());
        for (InventoryCanal inventoryCanal : InventoryCanal.inventoryCanals){

            if (player.getName().equalsIgnoreCase(inventoryCanal.getTarget().getName())){
                System.out.println("isTarget");
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();
                inventoryCanal.targetInvBuilder();

            }else if (player.getName().equalsIgnoreCase(inventoryCanal.getViewer().getName())){
                System.out.println("isViewer");
                inventoryCanal.targetInvBuilder();
                inventoryCanal.getInventory().setContent(inventoryCanal.viewerInvBuilder());
                inventoryCanal.getViewer().updateInventory();

            }

        }
    }
}
