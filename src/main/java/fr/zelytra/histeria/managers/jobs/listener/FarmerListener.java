/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.jobs.listener;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Farmer;
import fr.zelytra.histeria.managers.jobs.utils.JobUtils;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class FarmerListener implements Listener {

    private static final HashMap<Material, Integer> seedXpMap = new HashMap<>();
    private static final HashMap<Material, Integer> blockXpMap = new HashMap<>();
    private static final HashMap<EntityType, Integer> entityXpMap = new HashMap<>();

    {
        seedXpMap.put(Material.WHEAT, 7);
        seedXpMap.put(Material.POTATOES, 10);
        seedXpMap.put(Material.BEETROOTS, 6);
        seedXpMap.put(Material.CARROTS, 10);
        seedXpMap.put(Material.COCOA, 15);

        blockXpMap.put(Material.MELON, 9);
        blockXpMap.put(Material.PUMPKIN, 9);

        entityXpMap.put(EntityType.PIG, 15);
        entityXpMap.put(EntityType.COW, 15);
        entityXpMap.put(EntityType.SHEEP, 15);
        entityXpMap.put(EntityType.RABBIT, 30);
        entityXpMap.put(EntityType.CHICKEN, 15);
        entityXpMap.put(EntityType.FOX, 40);

        entityXpMap.put(EntityType.GOAT, 60);
        entityXpMap.put(EntityType.MUSHROOM_COW, 100);

    }

    @EventHandler
    public void onSeedBreak(BlockBreakEvent e) {

        if (!seedXpMap.containsKey(e.getBlock().getType())) return;

        // Checking age of the seed
        BlockData data = e.getBlock().getBlockData();
        if (!(data instanceof Ageable)) return;
        Ageable blockData = (Ageable) data;
        if (blockData.getAge() != blockData.getMaximumAge()) return;

        // Job consuming experience
        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        if (player == null) return;

        Farmer job = (Farmer) player.getJob(JobType.FARMER);
        int xp = seedXpMap.get(e.getBlock().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);


    }

    @EventHandler
    public void onSpawnerCraft(PrepareItemCraftEvent e) {
        ItemStack result = e.getInventory().getResult();
        if (result != null && result.getType() == Material.SPAWNER) {

            CustomPlayer player = CustomPlayer.getCustomPlayer(e.getView().getPlayer().getName());

            if (player != null && player.getJob(JobType.FARMER).getLevel() != 100)
                e.getInventory().setResult(null);
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {

        if (e.getEntity().getKiller() == null) return;
        if (!entityXpMap.containsKey(e.getEntity().getType())) return;

        // Job consuming experience
        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getEntity().getKiller().getName());
        if (player == null) return;

        Farmer job = (Farmer) player.getJob(JobType.FARMER);
        int xp = entityXpMap.get(e.getEntity().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);


    }


    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {

        if (!blockXpMap.containsKey(e.getBlock().getType())) return;

        // Block logger checker (to avoid player abuse place and break abuse for infinite xp)
        PersistentDataContainer chunkData = e.getBlock().getChunk().getPersistentDataContainer();
        if (chunkData.has(generateLocKey(e.getBlock().getLocation()))) {
            chunkData.remove(generateLocKey(e.getBlock().getLocation()));
            return;
        }

        CustomPlayer player = CustomPlayer.getCustomPlayer(e.getPlayer().getName());
        if (player == null) return;

        Farmer job = (Farmer) player.getJob(JobType.FARMER);
        int xp = blockXpMap.get(e.getBlock().getType());

        if (job.consumeXP(xp, player))
            JobUtils.displayXP(job.getJob(), player, xp);

    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (!blockXpMap.containsKey(e.getBlock().getType())) return;

        PersistentDataContainer chunkData = e.getBlock().getChunk().getPersistentDataContainer();

        if (chunkData.has(generateLocKey(e.getBlock().getLocation()))) return;

        chunkData.set(generateLocKey(e.getBlock().getLocation()), PersistentDataType.STRING, "jobBlockLogs_farmer");


    }

    private NamespacedKey generateLocKey(Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
    }
}
