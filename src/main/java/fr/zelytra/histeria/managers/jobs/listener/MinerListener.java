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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;

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

        if (!canConsumeBlock(e.getBlock())) return;
        if (e.isCancelled()) return;

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        if (player == null) return;

        Miner job = (Miner) player.getJob(JobType.MINER);
        int xp = xpMap.get(e.getBlock().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);

    }

    @EventHandler
    public void onDrillCraft(PrepareItemCraftEvent e) {
        ItemStack result = e.getInventory().getResult();
        if (result != null && result.getType() == CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) {

            CustomPlayer player = CustomPlayer.getCustomPlayer(e.getView().getPlayer().getName());

            if (player != null && player.getJob(JobType.MINER).getLevel() != 100)
                e.getInventory().setResult(null);
        }
    }

    private static boolean canConsumeBlock(Block block) {
        if (!xpMap.containsKey(block.getType())) return false;

        // Block logger checker (to avoid player abuse place and break abuse for infinite xp)
        PersistentDataContainer chunkData = block.getChunk().getPersistentDataContainer();
        if (chunkData.has(generateLocKey(block.getLocation()))) {
            chunkData.remove(generateLocKey(block.getLocation()));
            System.out.println("is placed");
            return false;
        }
        System.out.println("is natural");
        return true;

    }

    public static void consumeBlocksXP(Player player, List<Block> blocksList) {

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayer(player.getName());
        if (player == null) return;

        Miner job = (Miner) customPlayer.getJob(JobType.MINER);
        int xp = 0;

        for (Block block : blocksList) {
            if (!canConsumeBlock(block)) continue;
            xp += xpMap.get(block.getType());
        }

        if (job.consumeXP(xp, customPlayer))
            JobUtils.displayXP(job.getJob(), customPlayer, xp);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (!xpMap.containsKey(e.getBlock().getType())) return;

        PersistentDataContainer chunkData = e.getBlock().getChunk().getPersistentDataContainer();

        if (chunkData.has(generateLocKey(e.getBlock().getLocation()))) return;

        chunkData.set(generateLocKey(e.getBlock().getLocation()), PersistentDataType.STRING, "jobBlockLogs_miner");

    }

    private static NamespacedKey generateLocKey(Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
    }


}
