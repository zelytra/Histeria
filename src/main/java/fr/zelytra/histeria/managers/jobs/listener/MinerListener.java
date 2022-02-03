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

    private static final HashMap<Material, Integer> xpMap = new HashMap<>();

    {
        xpMap.put(Material.STONE, 0);

        xpMap.put(Material.COAL_ORE, 5);
        xpMap.put(Material.DEEPSLATE_COAL_ORE, 50);

        xpMap.put(Material.IRON_ORE, 10);
        xpMap.put(Material.DEEPSLATE_IRON_ORE, 15);
        xpMap.put(Material.RAW_IRON_BLOCK, 30);

        xpMap.put(Material.COPPER_ORE, 20);
        xpMap.put(Material.DEEPSLATE_COPPER_ORE, 25);
        xpMap.put(Material.RAW_COPPER_BLOCK, 45);

        xpMap.put(Material.GOLD_ORE, 35);
        xpMap.put(Material.DEEPSLATE_GOLD_ORE, 45);
        xpMap.put(Material.RAW_GOLD_BLOCK, 50);
        xpMap.put(Material.NETHER_GOLD_ORE, 30);
        xpMap.put(Material.GILDED_BLACKSTONE, 60);

        xpMap.put(Material.REDSTONE_ORE, 20);
        xpMap.put(Material.DEEPSLATE_REDSTONE_ORE, 25);

        xpMap.put(Material.EMERALD_ORE, 65);
        xpMap.put(Material.DEEPSLATE_EMERALD_ORE, 70);

        xpMap.put(Material.AMETHYST_CLUSTER, 10);

        xpMap.put(Material.LAPIS_ORE, 20);
        xpMap.put(Material.DEEPSLATE_LAPIS_ORE, 20);

        xpMap.put(Material.DIAMOND_ORE, 40);
        xpMap.put(Material.DEEPSLATE_DIAMOND_ORE, 35);

        xpMap.put(CustomMaterial.HISTERITE_ORE.getVanillaMaterial(), 30);

        xpMap.put(Material.NETHER_QUARTZ_ORE, 5);

        xpMap.put(Material.ANCIENT_DEBRIS, 60);

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

        chunkData.set(generateLocKey(e.getBlock().getLocation()), PersistentDataType.STRING, "jobBlockLogs_miner");


    }

    private NamespacedKey generateLocKey(Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
    }


}
