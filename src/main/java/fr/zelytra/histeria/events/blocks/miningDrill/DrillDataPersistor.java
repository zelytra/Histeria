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
import fr.zelytra.histeria.managers.logs.LogType;

import java.io.*;

public abstract class DrillDataPersistor {

    public static void saveAll() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "MiningDrills";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdir();

        for (var drill : MiningDrill.miningDrillInstance.entrySet()) {
            try {

                File file = new File(folderPath + File.separator + drill.getKey() + ".drill");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(drill.getValue());
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Histeria.log(MiningDrill.miningDrillInstance.size() + " mining drills has been saved", LogType.INFO);
    }

    public static void load() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "MiningDrills";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
            return;
        }

        for (File file : folder.listFiles()) {

            try {

                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                MiningDrill drill = (MiningDrill) oos.readObject();
                MiningDrill.miningDrillInstance.put(drill.getUuid(), drill);

                oos.close();

            } catch (Exception e) {
                Histeria.log("Wrong Drill file format for : " + file.getName(), LogType.ERROR);
                file.delete();
            }

        }

        Histeria.log(MiningDrill.miningDrillInstance.size() + " mining drills has been loaded", LogType.INFO);
    }

}
