/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.market.stand;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;

import java.io.*;

public abstract class StandSerializer {

    public static void saveAll() {
        String currentDir = Histeria.getInstance().getDataFolder().getPath();
        File folder = new File(currentDir + File.separator + "stands");

        if (!folder.exists())
            folder.mkdir();

        for (Stand stand : Stand.serverStands) {
            try {

                File file = new File(folder.getPath() + File.separator + stand.getUUID() + ".stand");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(stand);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadAll() {
        String currentDir = Histeria.getInstance().getDataFolder().getPath();
        File folder = new File(currentDir + File.separator + "stands");
        folder.mkdir();

        Histeria.log("Loading stands...", LogType.INFO);
        for (File file : folder.listFiles()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Stand.serverStands.add((Stand) ois.readObject());
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Histeria.log(Stand.serverStands.size() + " stands loaded !", LogType.INFO);
    }

    public static void remove(Stand stand) {
        String currentDir = Histeria.getInstance().getDataFolder().getPath();
        File folder = new File(currentDir + File.separator + "stands");
        File file = new File(folder.getPath() + File.separator + stand.getUUID() + ".stand");

        if (file.exists())
            file.delete();

    }
}
