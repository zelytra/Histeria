package fr.zelytra.histeria.events.blocks;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CoreMiningDrill implements Listener {

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

        drill.destroy();
    }

    @EventHandler
    public void openDrill(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getHand() != EquipmentSlot.HAND) return;
        if (e.getClickedBlock().getType() != CustomMaterial.CORE_MINING_DRILL.getVanillaMaterial()) return;

        MiningDrill drill = MiningDrill.getDrill(e.getClickedBlock());
        if (drill == null) return;
        System.out.println("trigger");
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

class MiningDrill {

    private final static Map<UUID, MiningDrill> miningDrillInstance = new HashMap<>();
    private final static Random random = new Random();

    private final List<OreContainer> oreContainerList = new ArrayList<>();
    private final UUID uuid = UUID.randomUUID();
    private final NamespacedKey idKey;
    private final Block block;

    private long timeFromLastPickup; // in secondes
    private final int drawTime = 2; //Time in seconds to wait before new draw
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

        this.idKey = keyBuilder(block.getLocation());
        this.block = block;
        this.timeFromLastPickup = System.currentTimeMillis() / 3600;

        // Righting uuid in chunk PDC
        PersistentDataContainer dataContainer = block.getChunk().getPersistentDataContainer();
        dataContainer.set(idKey, PersistentDataType.STRING, uuid.toString());

        miningDrillInstance.put(uuid, this);

    }

    /**
     * Draw ores count and update ore number containing by the drill
     *
     * @param now Current time in millis
     */
    public void draw(long now) {
        int drawCount = (int) (((now / 3600) - timeFromLastPickup) / drawTime);
        if (drawCount == 0) return;
        System.out.println(drawCount);
        for (int count = 0; count <= drawCount; count++) {
            for (OreContainer ore : oreContainerList) {

                if (ore.luck >= random.nextInt(0, 100))
                    ore.increment();

            }
        }

    }

    private static NamespacedKey keyBuilder(@NotNull Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getX() + "_" + location.getY() + "_" + location.getZ());
    }

    public void destroy() {
        PersistentDataContainer dataContainer = block.getChunk().getPersistentDataContainer();
        dataContainer.remove(idKey);
        miningDrillInstance.remove(uuid);
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
        timeFromLastPickup = System.currentTimeMillis() / 3600;
        for (ItemStack item : content) {
            if (item == null) continue;
            for (OreContainer container : oreContainerList) {
                if (container.ore == item.getType()) {
                    container.count = item.getAmount();
                    break;
                }
                container.count = 0;
            }
        }
    }
}

class OreContainer {

    public final Material ore;
    public final int luck;
    public int count = 0;

    public OreContainer(Material material, int luck) {
        this.ore = material;
        this.luck = luck;
    }

    public void increment() {
        if (count <= ore.getMaxStackSize())
            count++;
    }
}

