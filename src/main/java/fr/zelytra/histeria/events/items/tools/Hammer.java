package fr.zelytra.histeria.events.items.tools;

import com.massivecraft.factions.*;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.events.items.itemHandler.DurabilityHandler;
import fr.zelytra.histeria.events.items.itemHandler.SlotEnum;
import fr.zelytra.histeria.managers.event.customItem.CustomItemEvent;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Hammer implements Listener {
    private final CustomMaterial customMaterial = CustomMaterial.HAMMER;
    private final int range = 1;

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (CustomItemStack.hasCustomItemInMainHand(customMaterial.getName(), e.getPlayer())) {
            Player player = e.getPlayer();
            CustomItemEvent customItemEvent = new CustomItemEvent(customMaterial,e.getPlayer());
            Bukkit.getPluginManager().callEvent(customItemEvent);

            if(customItemEvent.isCancelled()){
                return;
            }

            Location BLocation = e.getBlock().getLocation();
            Material block = e.getBlock().getType();
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        BLocation.setX(e.getBlock().getX() + x);
                        BLocation.setY(e.getBlock().getY() + y);
                        BLocation.setZ(e.getBlock().getZ() + z);
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
                                || BLocation.getBlock().getType() == Material.END_PORTAL_FRAME) {
                            continue;
                        }
                        BLocation.getBlock().breakNaturally();

                    }
                }
            }
            DurabilityHandler durabilityHandler = new DurabilityHandler(player, customMaterial, SlotEnum.MAIN_HAND);
            durabilityHandler.iterate();
        }
    }
}
