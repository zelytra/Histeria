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
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private static List<Hologram> holograms = new ArrayList<>();

    private String tag;
    private CLocation location;
    private List<HoloLine> lineList = new ArrayList<>();

    /**
     *
     * @param location
     * @param content
     */

    public Hologram(Location location, String content) {
        this.location = new CLocation(location);

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

        holograms.remove(this);
    }

    public void addLine(String content) {
        Location lineLoc = location.getLocation().clone();
        lineLoc.setY(lineLoc.getY() - (0.25f * (lineList.size() + 1)));
        lineList.add(new HoloLine(lineList.size() + 1, content, lineLoc));
    }

    public void move(Location newLocation) {

    }


}
