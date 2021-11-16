/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.hologram;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.utils.CLocation;
import org.bukkit.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Hologram implements Serializable {

    public static final List<Hologram> holograms = new ArrayList<>();

    private String tag;
    private final CLocation location;
    private final List<HoloLine> lineList = new ArrayList<>();

    /**
     * @param location
     * @param content
     */

    public Hologram(Location location, String name, String content) {
        this.location = new CLocation(location);
        this.tag = name;

        String[] lines = content.split("#");

        for (int x = 0; x < lines.length; x++) {
            Location lineLoc = location.clone();
            lineLoc.setY(lineLoc.getY() - (0.25f * x));
            lineList.add(new HoloLine(x, lines[x], lineLoc));
        }
        holograms.add(this);
    }

    public void destroy() {
        for (HoloLine line : lineList)
            line.destroy();

        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "Holograms";
        File file = new File(folderPath + File.separator + tag + ".holo");

        if (file.exists())
            file.delete();

        holograms.remove(this);
    }

    public void addLine(String content) {

        Location lineLoc = location.getLocation().clone();
        lineLoc.setY(lineLoc.getY() - (0.25f * lineList.size()));
        lineList.add(new HoloLine(lineList.size() + 1, content, lineLoc));

    }

    public void move(Location newLocation) {
        Location ref = lineList.get(0).getLocation();

        double dx = newLocation.getX() - ref.getX(),
                dy = newLocation.getY() - ref.getY(),
                dz = newLocation.getZ() - ref.getZ();

        for (HoloLine line : lineList) {
            line.move(new Location(line.getLocation().getWorld(),
                    line.getLocation().getX() + dx,
                    line.getLocation().getY() + dy,
                    line.getLocation().getZ() + dz
            ));
        }
    }

    public boolean editLine(int lineNumber, String content) {

        if (lineNumber >= lineList.size()) return false;

        lineList.get(lineNumber).changeText(content);
        return true;

    }

    public static boolean exist(String tag) {
        for (Hologram hologram : holograms)
            if (hologram.tag.equalsIgnoreCase(tag))
                return true;
        return false;
    }

    public static Hologram get(String tag) {
        for (Hologram hologram : holograms)
            if (hologram.tag.equalsIgnoreCase(tag))
                return hologram;
        return null;
    }

    public static void save() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "Holograms";
        File folder = new File(folderPath);

        if (!folder.exists())
            folder.mkdir();

        for (Hologram hologram : holograms) {

            try {

                File file = new File(folderPath + File.separator + hologram.tag + ".holo");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(hologram);
                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Histeria.log("§6" + holograms.size() + "§a Holograms has been saved", LogType.INFO);
    }

    public static void load() {
        String folderPath = Histeria.getInstance().getDataFolder().getPath() + File.separator + "Holograms";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
            return;
        }

        for (File file : folder.listFiles()) {

            try {

                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
                Hologram hologram = (Hologram) oos.readObject();
                holograms.add(hologram);

                for (HoloLine holoLine : hologram.getLineList())
                    holoLine.reload();

                oos.close();

            } catch (Exception e) {
                Histeria.log("§cWrong Holograms file format for : " + file.getName(), LogType.ERROR);
                file.delete();
            }

        }

        Histeria.log("§6" + holograms.size() + "§a Holograms has been loaded", LogType.INFO);


    }

    public String getTag() {
        return tag;
    }

    public CLocation getLocation() {
        return location;
    }

    public List<HoloLine> getLineList() {
        return lineList;
    }
}
