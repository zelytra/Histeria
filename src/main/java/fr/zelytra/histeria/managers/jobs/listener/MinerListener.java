package fr.zelytra.histeria.managers.jobs.listener;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Miner;
import fr.zelytra.histeria.managers.jobs.utils.JobUtils;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class MinerListener implements Listener {

    private static final HashMap<Material, Double> xpMap = new HashMap<>();

    {
        xpMap.put(Material.STONE, 1.0);
        xpMap.put(Material.COAL_ORE, 10.0);
        xpMap.put(Material.IRON_ORE, 100000.0);
        xpMap.put(Material.GOLD_ORE, 2.0);
        xpMap.put(Material.DIAMOND_ORE, 2.0);
        xpMap.put(CustomMaterial.HISTERITE_ORE.getVanillaMaterial(), 2.0);
        xpMap.put(Material.EMERALD_ORE, 2.0);
        xpMap.put(Material.ANCIENT_DEBRIS, 2.0);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {

        if (!xpMap.containsKey(e.getBlock().getType())) return;

        // Block logger checker (to avoid player abuse place and break abuse for infinite xp)
        PersistentDataContainer chunkData = e.getBlock().getChunk().getPersistentDataContainer();
        if (chunkData.has(generateLocKey(e.getBlock().getLocation()))) {
            chunkData.remove(generateLocKey(e.getBlock().getLocation()));
            return;
        }

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        if (player == null) return;

        Miner job = (Miner) player.getJob(JobType.MINER);
        double xp = xpMap.get(e.getBlock().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (!xpMap.containsKey(e.getBlock().getType())) return;

        PersistentDataContainer chunkData = e.getBlock().getChunk().getPersistentDataContainer();

        if (chunkData.has(generateLocKey(e.getBlock().getLocation()))) return;

        chunkData.set(generateLocKey(e.getBlock().getLocation()), PersistentDataType.STRING, "jobBlockLogs");


    }

    private NamespacedKey generateLocKey(Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
    }


}
