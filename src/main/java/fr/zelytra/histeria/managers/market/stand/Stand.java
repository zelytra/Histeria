package fr.zelytra.histeria.managers.market.stand;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Stand implements Serializable {

    public static NamespacedKey shopKey = new NamespacedKey(Histeria.getInstance(), "stand");
    public static List<Stand> serverStands = new ArrayList<>();
    public static String shopName = "§6Stand";

    private String base64Item;
    private int price;

    private int x, y, z;
    private String world;

    private final String uuid = UUID.randomUUID().toString();

    public Stand(Location location, int price, ItemStack item) {
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        location.setPitch(0);
        location.setYaw(0);

        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.world = location.getWorld().getName();

        this.base64Item = itemStackToBase64(item);
        this.price = price;
        spawnStand(location);
        serverStands.add(this);
        StandSerializer.saveAll();
    }

    public static Stand getStand(String uuid) {
        for (Stand stand : serverStands)
            if (stand.getUUID().equals(uuid))
                return stand;
        return null;
    }

    public static Stand getStand(ItemStack item) {
        if (item.getItemMeta().getPersistentDataContainer().has(shopKey, PersistentDataType.STRING)) {
            PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
            for (Stand stand : serverStands)
                if (stand.getUUID().equals(dataContainer.get(shopKey, PersistentDataType.STRING)))
                    return stand;
        }
        return null;
    }

    public String getUUID() {
        return this.uuid;
    }

    private void spawnStand(Location location) {

        // Spawning Item
        Item i = location.getWorld().dropItem(location, getItem());
        i.setGravity(false);
        i.setVelocity(new Vector(0, 0, 0));
        i.setCustomNameVisible(true);
        i.setCanPlayerPickup(false);
        i.setCanMobPickup(false);
        i.getPersistentDataContainer().set(shopKey, PersistentDataType.STRING, uuid);

        // Glasse armorstant
        Location standPos = location;
        standPos.setY(location.getY() - 1.38);
        ArmorStand as = location.getWorld().spawn(standPos, ArmorStand.class);
        configStand(as);
        LivingEntity armorStand = as;
        ItemStack glass = new ItemStack(Material.GLASS);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setCustomModelData(18);
        glass.setItemMeta(glassMeta);
        armorStand.getEquipment().setHelmet(glass);

        // ItemName Armorstand
        standPos.setY(standPos.getY() + 0.58);
        as = location.getWorld().spawn(standPos, ArmorStand.class);

        if (CustomItemStack.getCustomMaterial(getItem()) != null)
            as.setCustomName("§b" + CustomItemStack.getCustomMaterial(getItem()).getDisplayName());
        else
            as.setCustomName("§b" + getItem().getType().name().replace("_", " "));

        as.setCustomNameVisible(true);
        configStand(as);

        // Price Armorstand
        standPos.setY(standPos.getY() - 0.25);
        as = location.getWorld().spawn(standPos, ArmorStand.class);
        configStand(as);
        as.setCustomName("§6" + price + " §f" + Emote.GOLD);
        as.setCustomNameVisible(true);


    }

    private void configStand(ArmorStand armorStand) {
        armorStand.setGravity(false);
        armorStand.setCanPickupItems(false);
        armorStand.setVisible(false);
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.getPersistentDataContainer().set(shopKey, PersistentDataType.STRING, uuid);
    }


    public void openInterface(Player player) {
        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(27, shopName);
        interfaceBuilder.setContent(getContent());
        interfaceBuilder.open(player);
    }

    private ItemStack[] getContent() {
        ItemStack[] content = new ItemStack[27];

        for (int x = 0; x <= 8; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        for (int x = 18; x <= 26; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        content[2] = VisualType.BLANK_GREEN_GLASSE.getItem();
        content[20] = VisualType.BLANK_GREEN_GLASSE.getItem();
        content[6] = VisualType.BLANK_RED_GLASSE.getItem();
        content[24] = VisualType.BLANK_RED_GLASSE.getItem();

        content[15] = new VisualItemStack(Material.BARRIER, "§cClose", false).getItem();
        content[11] = VisualType.VALIDAY.getItem();

        ItemStack item = getItem().clone();
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6" + price + " §f" + Emote.GOLD);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(shopKey, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        content[13] = item;
        return content;
    }

    public ItemStack getItem() {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(this.base64Item));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            ItemStack item = (ItemStack) dataInput.readObject();

            dataInput.close();
            return item;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getItemName() {
        if (CustomItemStack.getCustomMaterial(getItem()) != null)
            return "§b" + CustomItemStack.getCustomMaterial(getItem()).getDisplayName();
        else
            return "§b" + getItem().getType().name().replace("_", " ");

    }

    public int getPrice() {
        return price;
    }

    private String itemStackToBase64(ItemStack item) {
        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public void destroy() {
        serverStands.remove(this);
        StandSerializer.remove(this);

        /* Entity killer */
        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        Collection<Entity> entities = location.getNearbyEntities(1,1,1);

        for(Entity entity : entities){
            if (entity instanceof ArmorStand || entity instanceof Item)
                entity.remove();
        }

    }
}
