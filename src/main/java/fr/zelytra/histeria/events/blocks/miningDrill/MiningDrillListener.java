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
import fr.zelytra.histeria.managers.languages.LangMessage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class MiningDrillListener implements Listener {

    private final static int LOCKING_AREA_XYZ = 3; //Locking area

    @EventHandler
    public void drillPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;

        Location drillLocation = e.getBlock().getLocation();

        // Checking bedrock under drill
        Location bedrockLocation = drillLocation.clone();
        bedrockLocation.setY(bedrockLocation.getBlockY() - 1);
        if (e.getBlock().getWorld().getBlockAt(bedrockLocation) == null || e.getBlock().getWorld().getBlockAt(bedrockLocation).getType() != Material.BEDROCK) {
            e.setCancelled(true);
            LangMessage.sendMessage(e.getPlayer(), "drill.placeBedrock");
            return;
        }

        // Checking block around
        for (int x = drillLocation.getBlockX() - 1; x <= drillLocation.getBlockX(); x++) {
            for (int y = drillLocation.getBlockY() - 1; y <= drillLocation.getBlockY(); y++) {
                for (int z = drillLocation.getBlockZ() - 1; z <= drillLocation.getBlockZ(); z++) {
                    Block block = e.getBlock().getWorld().getBlockAt(x, y, z);

                    if (block.getLocation().equals(drillLocation)) continue;
                    if (block != null && (block.getType() == Material.AIR || block.getType() == Material.BEDROCK))
                        continue;

                    e.setCancelled(true);
                    LangMessage.sendMessage(e.getPlayer(), "drill.placeSurround");
                    return;

                }
            }
        }
        new MiningDrill(e.getBlock());
    }

    @EventHandler
    public void blockRestriction(BlockPlaceEvent e) {
        Location blockLocation = e.getBlock().getLocation();

        // Checking drill around
        for (int x = blockLocation.getBlockX() - 1; x <= blockLocation.getBlockX() + 1; x++) {
            for (int y = blockLocation.getBlockY() - 1; y <= blockLocation.getBlockY() + 1; y++) {
                for (int z = blockLocation.getBlockZ() - 1; z <= blockLocation.getBlockZ() + 1; z++) {

                    Block block = e.getBlock().getWorld().getBlockAt(x, y, z);
                    if (block.getLocation().equals(blockLocation)) continue;
                    if (block != null && block.getType() == CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) {
                        e.setCancelled(true);
                        LangMessage.sendMessage(e.getPlayer(), "drill.restriction");
                        return;
                    }

                }
            }
        }
    }

    @EventHandler
    public void drillBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;


        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.getBlock().breakNaturally();
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()));
        }

        MiningDrill drill = MiningDrill.getDrill(e.getBlock());
        if (drill == null) return;

        drill.destroy(e.getBlock());
    }

    @EventHandler
    public void openDrill(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;

        if (e.getPlayer().isSneaking()) return;
        e.setCancelled(true);

        MiningDrill drill = MiningDrill.getDrill(e.getClickedBlock());
        if (drill == null) return;

        if (drill.viewer != null) return;

        drill.openUI(e.getPlayer());

    }

    @EventHandler
    public void drillInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().equals("§6Mining Drill")) {

            MiningDrill drill = MiningDrill.getDrill(e.getPlayer().getName());
            if (drill == null) return;

            drill.viewer = null;
            drill.updateOreCount(e.getInventory().getContents());

            //Checking obstructed item
            for (ItemStack item : e.getInventory().getContents()) {
                if (item == null) continue;
                boolean isOre = false;

                // Check item type ore
                for (OreContainer oreContainer : drill.getOreContainerList()) {
                    isOre = item.getType() == oreContainer.ore;
                    if (isOre) break;
                }

                // Returning obstructed item in player inventory
                if (!isOre)
                    e.getPlayer().getInventory().addItem(item);


            }

        }
    }

    @EventHandler
    public void pistonExtendEvent(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks()) {
            if (block.getType() == CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void pistonRetractEvent(BlockPistonRetractEvent e) {
        for (Block block : e.getBlocks()) {
            if (block.getType() == CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) {
                e.setCancelled(true);
            }
        }
    }


}

