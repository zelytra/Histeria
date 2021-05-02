package fr.zelytra.histeria.events.blocks;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.CustomGUI;
import fr.zelytra.histeria.builder.InterfaceBuilder;
import fr.zelytra.histeria.builder.VisualType;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NocturiteGenerator implements Listener {
    private final int generationTime = 5;//1800;//temps de génération en secondes
    private final String interfaceName = "§5Nocturite Generator Interface";
    private static final HashMap<NamespacedKey, Player> noctInventorywatcher = new HashMap<>();


    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()) {
            PersistentDataContainer data = e.getBlockPlaced().getChunk().getPersistentDataContainer();
            data.set(keyBuilder(e.getBlockPlaced().getLocation()), PersistentDataType.LONG, System.currentTimeMillis());
        }

    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getBlock().getType() == CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()) {
            if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                e.getBlock().breakNaturally(new ItemStack(CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()));
            }
            PersistentDataContainer data = e.getBlock().getChunk().getPersistentDataContainer();
            data.remove(keyBuilder(e.getBlock().getLocation()));
        }

    }

    @EventHandler
    public void pistonExtendEvent(BlockPistonExtendEvent e) {
        for (Block block:e.getBlocks()) {
            if(block.getType()==CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void pistonRetractEvent(BlockPistonRetractEvent e) {
        for (Block block:e.getBlocks()) {
            if(block.getType()==CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK && Objects.requireNonNull(e.getClickedBlock()).getType() == CustomMaterial.NOCTURITE_GENERATOR.getVanillaMaterial() && !e.getPlayer().isSneaking()) {
            Player player = e.getPlayer();
            e.setCancelled(true);
            NamespacedKey blockKey = keyBuilder(e.getClickedBlock().getLocation());
            long time = e.getClickedBlock().getChunk().getPersistentDataContainer().get(blockKey, PersistentDataType.LONG);
            if (noctInventorywatcher.containsKey(blockKey)) {
                player.sendMessage(Message.getPlayerPrefixe() + "§cYou cannot access this block at the same time as another player.");
                return;
            }
            noctInventorywatcher.put(blockKey, player);
            InterfaceBuilder gui = new InterfaceBuilder(27, interfaceName);
            gui.setContent(contentBuilder(time));
            gui.open(player);

        }

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().equals(interfaceName)) {
            NamespacedKey toRemove = null;
            for (Map.Entry<NamespacedKey, Player> entry : noctInventorywatcher.entrySet()) {
                if (entry.getValue() == e.getPlayer()) {
                    toRemove = entry.getKey();
                    break;
                }
            }
            if (toRemove != null) {
                noctInventorywatcher.remove(toRemove);
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof CustomGUI && e.getView().getTitle().equals(interfaceName)) {
            if (e.getCurrentItem() != null && CustomItemStack.hasTag(e.getCurrentItem())) {
                e.setCancelled(true);
                CustomMaterial material = CustomMaterial.getByName(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(CustomItemStack.getItemKey(), PersistentDataType.STRING));
                switch (material) {
                    case NOCTURITE_CORE:
                        return;
                    case NOCTURITE_NUGGET:
                        String s = e.getCurrentItem().getItemMeta().getDisplayName();
                        String[] number = s.split(" ");
                        int nuggetAmount = Integer.parseInt(number[0].replace("§5", ""));
                        if (nuggetAmount > 0) {
                            ItemStack nuggets = new CustomItemStack(CustomMaterial.NOCTURITE_NUGGET, nuggetAmount).getItem();
                            e.getWhoClicked().getInventory().addItem(nuggets);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 2);
                            }
                            for (Map.Entry<NamespacedKey, Player> entry : noctInventorywatcher.entrySet()) {
                                if (entry.getValue() == e.getWhoClicked()) {
                                    PersistentDataContainer data = getBlockContainer(entry.getKey(), (Player) e.getWhoClicked());
                                    data.set(entry.getKey(), PersistentDataType.LONG, System.currentTimeMillis());
                                    e.getWhoClicked().closeInventory();
                                    break;
                                }
                            }
                        }
                }
            }
        }
    }

    private NamespacedKey keyBuilder(Location location) {
        return new NamespacedKey(Histeria.getInstance(), location.getX() + "_" + location.getY() + "_" + location.getZ());
    }

    public ItemStack[] contentBuilder(long time) {
        ItemStack[] content = new ItemStack[27];
        for (int x = 0; x <= 26; x++) {
            content[x] = VisualType.BLANK_BLUE_GLASSE.getItem();
        }
        content[9] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[17] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[10] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[12] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[22] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[4] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[14] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();
        content[16] = VisualType.BLANK_LIGHT_BLUE_GLASSE.getItem();

        content[11] = buildTimeCore(time);
        content[15] = buildLastPickupCore(time);
        content[13] = buildPickupNugget(time);
        return content;
    }

    private ItemStack buildTimeCore(long time) {
        ItemStack item = new CustomItemStack(CustomMaterial.NOCTURITE_CORE, 1).getItem();
        ItemMeta meta = item.getItemMeta();

        long remainTime = System.currentTimeMillis() - time;
        long nextInS = remainTime / 1000;
        long Hours = nextInS / 3600;
        long Minutes = (nextInS % 3600) / 60;
        long Secondes = (nextInS % 60);

        meta.setDisplayName((int) Hours + "h" + (int) Minutes + "m" + (int) Secondes + "s");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Time elapsed since the last pickup.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack buildLastPickupCore(long time) {
        ItemStack item = new CustomItemStack(CustomMaterial.NOCTURITE_CORE, 1).getItem();
        ItemMeta meta = item.getItemMeta();

        long remainTime = (generationTime * 1000) - ((System.currentTimeMillis() - time) % (generationTime * 1000));
        long nextInS = remainTime / 1000;
        long Hours = nextInS / 3600;
        long Minutes = (nextInS % 3600) / 60;
        long Secondes = (nextInS % 60);

        meta.setDisplayName((int) Hours + "h" + (int) Minutes + "m" + (int) Secondes + "s");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Time remaining before next generation");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack buildPickupNugget(long time) {
        ItemStack item = new CustomItemStack(CustomMaterial.NOCTURITE_NUGGET, 1).getItem();
        ItemMeta meta = item.getItemMeta();
        int nuggetNumber = (int) ((System.currentTimeMillis() - time) / (generationTime * 1000));
        if (meta != null) {
            meta.setDisplayName("§5" + nuggetNumber + " nocturite nugget");
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Current number of nuggets generated.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private PersistentDataContainer getBlockContainer(NamespacedKey key, Player player) {
        return player.getWorld().getChunkAt(new Location(player.getWorld(), Double.parseDouble(key.getKey().split("_")[0]), Double.parseDouble(key.getKey().split("_")[1]), Double.parseDouble(key.getKey().split("_")[2]))).getPersistentDataContainer();
    }
}
