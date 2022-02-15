/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.miningDrill;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MiningDrillListener implements Listener {

    private final static int LOCKING_AREA_XYZ = 3; //Locking area

    @EventHandler
    public void drillPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;
        //TODO handle space between other blocks
        //TODO handle place on bedrock block
        new MiningDrill(e.getBlock());
    }

    @EventHandler
    public void drillBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;

        MiningDrill drill = MiningDrill.getDrill(e.getBlock());
        if (drill == null) return;

        drill.destroy(e.getBlock());
    }

    @EventHandler
    public void openDrill(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;

        MiningDrill drill = MiningDrill.getDrill(e.getClickedBlock());
        if (drill == null) return;

        drill.openUI(e.getPlayer());

    }

    @EventHandler
    public void drillInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().equals("§6Mining Drill")) {

            MiningDrill drill = MiningDrill.getDrill(e.getPlayer().getName());
            if (drill == null) return;

            drill.viewer = null;
            drill.updateOreCount(e.getInventory().getContents());
        }
    }

    //TODO Handle piston move events


}

