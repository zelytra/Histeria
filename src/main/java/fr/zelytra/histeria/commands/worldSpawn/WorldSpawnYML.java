/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.commands.worldSpawn;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WorldSpawnYML {

    public static String FOLDER_PATH = Histeria.getInstance().getDataFolder().getPath() + File.separator;
    private final File worldSpawnFile;
    private final FileConfiguration configFile;

    public WorldSpawnYML() {
        this.worldSpawnFile = new File(FOLDER_PATH + "WorldSpawn.yml");
        this.configFile = new YamlConfiguration();

        try {
            if (!worldSpawnFile.exists())
                worldSpawnFile.createNewFile();

            this.configFile.load(worldSpawnFile);

        } catch (IOException | InvalidConfigurationException ignored) {
        }
    }

    public void save(Location location) {
        configFile.set("x", location.getBlockX() + 0.5);
        configFile.set("y", location.getBlockY() + 0.5);
        configFile.set("z", location.getBlockZ() + 0.5);

        configFile.set("yaw", location.getYaw());
        configFile.set("pitch", location.getPitch());

        configFile.set("world", location.getWorld().getName());

        try {
            configFile.save(worldSpawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Location getSpawn() {
        if (configFile.get("x") == null)
            return new Location(Bukkit.getWorld("world"), 0, 100, 0, 0, 0);

        return new Location(
                Bukkit.getWorld(configFile.getString("world")),
                configFile.getDouble("x"),
                configFile.getDouble("y"),
                configFile.getDouble("z"),
                (float) configFile.getDouble("yaw"),
                (float) configFile.getDouble("pitch")
        );

    }
}
