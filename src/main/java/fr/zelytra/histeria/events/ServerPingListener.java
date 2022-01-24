/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {

        try {
            e.setServerIcon(Bukkit.loadServerIcon(getServerIcon()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        e.setMotd(" §6PvP§e-§6Faction  §3«§b« §a♦ §bHISTERIA §a♦ §b»§3»  §6Vanilla-Modded  §9*§b*§9*§b*§9*§b*§9*§b*§9*§b*§9*§b*§9*§b*§9*§b*§9*  §adiscord.histeria.fr  §9*§b*§9*§b*§9*§b*§9*§b*§c1.18.1§b*§9*§b*§9*§b*§9*§b*§9*");

    }

    private File getServerIcon() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/zelytra/Histeria/master/ressources/server-icon.png");
            BufferedImage img = ImageIO.read(url);
            File file = new File("server-icon.png");
            if (!file.exists())
                file.createNewFile();
            ImageIO.write(img, "png", file);
            return file;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
