/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.items.tools;

import com.massivecraft.factions.*;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.events.items.itemHandler.events.CustomItemBreakBlockEvent;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.listener.MinerListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Hammer implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HAMMER;
    private final int range = 1;

    @EventHandler
    public void breakBlock(CustomItemBreakBlockEvent e) {

        if (e.getMaterial() != customMaterial) return;
        if (e.isCancelled()) return;

        Player player = e.getPlayer();
        List<Block> blockToBreak = new ArrayList<>();


        Location BLocation = e.getEvent().getBlock().getLocation();
        Material block = e.getEvent().getBlock().getType();
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BLocation.setX(e.getEvent().getBlock().getX() + x);
                    BLocation.setY(e.getEvent().getBlock().getY() + y);
                    BLocation.setZ(e.getEvent().getBlock().getZ() + z);

                    if (Histeria.isSaberFaction()) {
                        FPlayer fplayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
                        FLocation fLoc = new FLocation(BLocation);
                        Faction faction = Board.getInstance().getFactionAt(fLoc);
                        if (fplayer.getFaction() != faction && !faction.isWilderness()) {
                            continue;
                        }
                    }

                    if (block != Material.OBSIDIAN && BLocation.getBlock().getType() == Material.OBSIDIAN) {
                        continue;
                    }

                    if (BLocation.getBlock().getType().equals(Material.BEDROCK)
                            || BLocation.getBlock().getType().equals(Material.COMMAND_BLOCK)
                            || BLocation.getBlock().getType().equals(Material.LODESTONE)
                            || BLocation.getBlock().getType().equals(Material.INFESTED_COBBLESTONE)
                            || BLocation.getBlock().getType() == Material.END_PORTAL_FRAME
                            || BLocation.getBlock().getType() == Material.CRYING_OBSIDIAN
                            || BLocation.getBlock().getType() == CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) {
                        continue;
                    }
                    blockToBreak.add(BLocation.getBlock());

                }
            }
        }
        e.setCancelled(true);
        MinerListener.consumeBlocksXP(player, blockToBreak);
        for (Block blocks : blockToBreak)
            blocks.breakNaturally(e.getItem(), true);

        DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);
        durabilityHandler.iterate();
    }
}

