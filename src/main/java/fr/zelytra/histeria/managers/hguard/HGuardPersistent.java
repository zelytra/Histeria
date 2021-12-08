/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.hguard;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.managers.logs.LogType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.BoundingBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class HGuardPersistent {
    public static String FOLDER_PATH = Histeria.getInstance().getDataFolder().getPath() + File.separator + "HGuard";

    public static void save(HGuard hGuard) {
        try {
            File hguardFolder = new File(FOLDER_PATH);
            if (!hguardFolder.exists()) {
                hguardFolder.mkdir();
            }
            File hguardFile = new File(hguardFolder + File.separator + hGuard.getName() + ".yml");
            if (!hguardFile.exists()) {
                hguardFile.createNewFile();
            }
            FileConfiguration configFile = new YamlConfiguration();
            configFile.load(hguardFile);

            configFile.set("Name", hGuard.getName());
            configFile.set("World", hGuard.getWorld().getName());
            configFile.set("Shape", hGuard.getShape().name());
            switch (hGuard.getShape()) {
                case CUBE:
                    configFile.set("minX", hGuard.getArea().getMinX());
                    configFile.set("minY", hGuard.getArea().getMinY());
                    configFile.set("minZ", hGuard.getArea().getMinZ());
                    configFile.set("maxX", hGuard.getArea().getMaxX());
                    configFile.set("maxY", hGuard.getArea().getMaxY());
                    configFile.set("maxZ", hGuard.getArea().getMaxZ());
                    break;
                case CYLINDER:
                    configFile.set("X", hGuard.getCenter().getBlockX());
                    configFile.set("Y", hGuard.getCenter().getBlockY());
                    configFile.set("Z", hGuard.getCenter().getBlockZ());
                    configFile.set("radius", hGuard.getRadius());
                    break;

            }
            configFile.set("Priority", hGuard.getPriorityLevel());
            List<String> materialList = new ArrayList<>();
            for (CustomMaterial material : hGuard.getCustomItemWhiteList()) {
                materialList.add(material.getName());
            }
            configFile.set("CustomMaterial", materialList);
            configFile.set("Groups", hGuard.getGroupWhiteList());

            materialList = new ArrayList<>();
            for (Material material : hGuard.getInteractWhiteList()) {
                materialList.add(material.name());
            }
            configFile.set("Interact", materialList);
            configFile.set("PlaceBlock", hGuard.canPlaceBlock());
            configFile.set("BreakBlock", hGuard.canBreakBlock());
            configFile.set("PVP", hGuard.isPvp());
            configFile.save(hguardFile);
            Histeria.log(hGuard.getName() + " area has been saved", LogType.INFO);

        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
            Histeria.log("Failed to save area : " + hGuard.getName(), LogType.ERROR);
        }

    }

    public static void load(File file) {
        try {
            FileConfiguration configFile = new YamlConfiguration();
            configFile.load(file);
            World world = Bukkit.getWorld(configFile.getString("World"));
            String name = configFile.getString("Name");
            Shape shape = Shape.valueOf(configFile.getString("Shape"));
            HGuard hGuard;

            switch (configFile.getString("Shape")) {
                case "CUBE":
                    BoundingBox boundingBox = new BoundingBox(configFile.getInt("minX"), configFile.getInt("minY"), configFile.getInt("minZ"), configFile.getInt("maxX"), configFile.getInt("maxY"), configFile.getInt("maxZ"));
                    hGuard = new HGuard(name, boundingBox, shape, world);
                    break;

                case "CYLINDER":
                    Location location = new Location(world, configFile.getInt("X"), configFile.getInt("Y"), configFile.getInt("Z"));
                    int radius = configFile.getInt("radius");
                    hGuard = new HGuard(name, location, shape, world, radius);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + configFile.getString("Shape"));
            }
            hGuard.setPriorityLevel(configFile.getInt("Priority"));
            hGuard.setBreakBlock(configFile.getBoolean("BreakBlock"));
            hGuard.setPlaceBlock(configFile.getBoolean("PlaceBlock"));
            hGuard.setPvp(configFile.getBoolean("PVP"));

            for (String material : configFile.getStringList("CustomMaterial")) {
                hGuard.addCustomItem(CustomMaterial.getByName(material));
            }

            for (String material : configFile.getStringList("Interact")) {
                hGuard.addInteract(Material.getMaterial(material));
            }

            for (String group : configFile.getStringList("Groups")) {
                hGuard.addGroupe(group);
            }
            Histeria.log(name + " area has been loaded", LogType.INFO);
            //Histeria.log(hGuard.toString(), LogType.INFO);

        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
            Histeria.log("Failed to load area : " + file.getName(), LogType.ERROR);
        }

    }
}
