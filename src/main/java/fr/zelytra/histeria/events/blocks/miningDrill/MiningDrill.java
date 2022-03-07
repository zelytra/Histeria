/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.miningDrill;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

class MiningDrill implements Serializable {

    public final static Map<UUID, MiningDrill> miningDrillInstance = new HashMap<>();
    private final transient static Random random = new Random();

    private final List<OreContainer> oreContainerList = new ArrayList<>();
    private final UUID uuid = UUID.randomUUID();

    private long timeFromLastPickup; // in secondes
    private final int drawTime = 60; //Time in seconds to wait before new draw
    public String viewer = null;

    {
        oreContainerList.add(new OreContainer(Material.RAW_COPPER, 15));
        oreContainerList.add(new OreContainer(Material.RAW_IRON, 15));
        oreContainerList.add(new OreContainer(Material.RAW_GOLD, 1));
        oreContainerList.add(new OreContainer(Material.COAL, 20));
        oreContainerList.add(new OreContainer(Material.DIAMOND, 5));
        oreContainerList.add(new OreContainer(Material.ANCIENT_DEBRIS, 1));
        oreContainerList.add(new OreContainer(Material.AMETHYST_SHARD, 10));
        oreContainerList.add(new OreContainer(Material.QUARTZ, 10));
        oreContainerList.add(new OreContainer(Material.LAPIS_LAZULI, 25));
    }

    public MiningDrill(Block block) {

        this.timeFromLastPickup = System.currentTimeMillis() / 3600;

        // Righting uuid in chunk PDC
        PersistentDataContainer dataContainer = block.getChunk().getPersistentDataContainer();
        dataContainer.set(keyBuilder(block.getLocation()), PersistentDataType.STRING, uuid.toString());

        miningDrillInstance.put(uuid, this);

    }

    /**
     * Draw ores count and update ore number containing by the drill
     *
     * @param now Current time in millis
     */
    public void draw(long now) {
        double timePaste = (now / 3600) - timeFromLastPickup;
        int drawCount = (int) (timePaste / drawTime);
        if (drawCount == 0) return;

        timeFromLastPickup = System.currentTimeMillis() / 3600;

        for (int count = 0; count < drawCount; count++) {
            for (OreContainer ore : oreContainerList) {
                if (ore.luck >= random.nextInt(0, 100)) {
                    ore.increment();
                    break;
                }
            }
        }
    }

    private static NamespacedKey keyBuilder(@NotNull Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getX() + "_" + location.getY() + "_" + location.getZ());
    }

    public void destroy(Block block) {
        PersistentDataContainer dataContainer = block.getChunk().getPersistentDataContainer();
        dataContainer.remove(keyBuilder(block.getLocation()));
        miningDrillInstance.remove(uuid);

        //Dropping content on the ground
        for (OreContainer ore : oreContainerList)
            block.getWorld().dropItem(block.getLocation(), new ItemStack(ore.ore, ore.count));
    }

    public static @Nullable MiningDrill getDrill(@NotNull Block block) {

        PersistentDataContainer dataContainer = block.getChunk().getPersistentDataContainer();
        NamespacedKey key = keyBuilder(block.getLocation());

        if (!dataContainer.has(key)) return null;

        String uuid = dataContainer.get(key, PersistentDataType.STRING);
        return miningDrillInstance.get(UUID.fromString(uuid));
    }

    public static @Nullable MiningDrill getDrill(@NotNull String viewerName) {

        for (var drills : miningDrillInstance.entrySet())
            if (drills.getValue().viewer != null && drills.getValue().viewer.equalsIgnoreCase(viewerName))
                return drills.getValue();

        return null;
    }


    public void openUI(Player player) {
        viewer = player.getName();
        draw(System.currentTimeMillis());
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(InventoryType.DISPENSER, "§6Mining Drill");

        // Filling inventory slot with draw ores
        ItemStack[] content = new ItemStack[InventoryType.DISPENSER.getDefaultSize()];
        for (int slot = 0; slot < InventoryType.DISPENSER.getDefaultSize(); slot++)
            content[slot] = new ItemStack(oreContainerList.get(slot).ore, oreContainerList.get(slot).count);

        interfaceBuilder.setContent(content);
        interfaceBuilder.open(player);
    }

    public void updateOreCount(ItemStack[] content) {
        for (OreContainer container : oreContainerList)
            container.count = 0;

        for (ItemStack item : content) {
            if (item == null) continue;
            for (OreContainer container : oreContainerList) {
                if (container.ore == item.getType()) {
                    container.count = item.getAmount();
                }
            }
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<OreContainer> getOreContainerList() {
        return oreContainerList;
    }
}
