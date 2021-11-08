/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.arena;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.utils.CLocation;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArenaChest implements Serializable {

    public static List<ArenaChest> chestList = new ArrayList<>();
    private CLocation location;
    private UUID uuid = UUID.randomUUID();

    public ArenaChest(Location location) {

        this.location = new CLocation(location);
        chestList.add(this);

    }

    public void destroy() {
        chestList.remove(this);
    }

    public void draw() {

    }

    public Location getLocation(){
        return this.location.getLocation();
    }

    public boolean isChest() {
        return location.getLocation().getBlock().getType() == Material.CHEST;
    }

    public static void startTask() {


    }

    public static void saveAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "ArenaChest";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdir();

        for (ArenaChest chest : chestList) {

            try {

                File file = new File(folderPath + File.separator + chest.uuid + ".ac");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(chest);
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Histeria.log("§6" + chestList.size() + "§a ArenaChest has been saved", LogType.INFO);
    }

    public static void loadAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "ArenaChest";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
            return;
        }

        for (File file : folder.listFiles()) {

            try {

                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                chestList.add((ArenaChest) oos.readObject());
                oos.close();

            } catch (Exception e) {
                Histeria.log("§cWrong ArenaChest file format for : " + file.getName(), LogType.ERROR);
                file.delete();
            }

        }

        Histeria.log("§6" + chestList.size() + "§a ArenaChest has been loaded", LogType.INFO);


    }

}
