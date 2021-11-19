/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.hologram;

import fr.zelytra.histeria.utils.CLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;

import java.io.Serializable;
import java.util.UUID;

public class HoloLine implements Serializable {

    private CLocation location;
    private final int lineNumber;
    private final String text;
    private transient ArmorStand stand;
    private final UUID uuid;

    public HoloLine(int lineNumber, String text, Location location) {

        this.location = new CLocation(location);
        this.lineNumber = lineNumber;
        this.text = text.replace("&", "§");
        this.stand = location.getWorld().spawn(location, ArmorStand.class);
        applyMetaData();
        this.uuid = stand.getUniqueId();

    }

    public void destroy() {
        if (Bukkit.getEntity(uuid) != null)
            Bukkit.getEntity(uuid).remove();
    }

    private void applyMetaData() {
        this.stand.setGravity(false);
        this.stand.setCanPickupItems(false);
        this.stand.setVisible(false);
        this.stand.setSmall(true);
        this.stand.setBasePlate(true);
        this.stand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        this.stand.setCustomName(text);
        this.stand.setCustomNameVisible(!text.isEmpty());

    }

    public void reload() {
        this.stand = (ArmorStand) Bukkit.getEntity(this.uuid);
    }

    public void move(Location newLocation) {
        this.location = new CLocation(newLocation);
        this.stand.teleport(newLocation);
    }

    public void changeText(String text) {
        this.stand.setCustomName(text);
    }

    public Location getLocation() {
        return location.getLocation();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getText() {
        return text;
    }
}
