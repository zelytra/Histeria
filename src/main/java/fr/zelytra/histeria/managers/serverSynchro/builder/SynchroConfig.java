/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.serverSynchro.builder;

import fr.zelytra.histeria.Histeria;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SynchroConfig {
    private FileConfiguration configuration;

    private String host;
    private int port;

    public SynchroConfig() {

        try {

            this.configuration = new YamlConfiguration();
            InputStream is = Histeria.getInstance().getResource("server.yml");
            Reader reader = new InputStreamReader(is);
            this.configuration.load(reader);

            this.host = this.configuration.getString("synchro.host");
            this.port = this.configuration.getInt("synchro.port");


        } catch (InvalidConfigurationException | IOException e) {

            Bukkit.getConsoleSender().sendMessage("§cYou are not running the plugin on the official server. Shutting down");
            Bukkit.shutdown();

        }

    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

}
