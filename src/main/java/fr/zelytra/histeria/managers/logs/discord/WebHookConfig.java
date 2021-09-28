/*
 * Copyright (c) 2021-2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.logs.discord;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class WebHookConfig {

    public FileConfiguration urlGetter;

    public WebHookConfig() {
        try {

            this.urlGetter = new YamlConfiguration();
            InputStream is = Histeria.getInstance().getResource("server.yml");
            Reader reader = new InputStreamReader(is);
            this.urlGetter.load(reader);

        } catch (InvalidConfigurationException | IOException e) {

            Bukkit.getConsoleSender().sendMessage("§cYou are not running the plugin on the official server. Shutting down");
            Bukkit.shutdown();

        }
    }

}
