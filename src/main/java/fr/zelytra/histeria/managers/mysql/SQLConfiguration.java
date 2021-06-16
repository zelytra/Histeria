package fr.zelytra.histeria.managers.mysql;

import fr.zelytra.histeria.Histeria;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SQLConfiguration {
    private FileConfiguration configuration;

    private String password;
    private String host;
    private String userName;
    private int port;
    private String database;

    public SQLConfiguration() {

        try {

            this.configuration = new YamlConfiguration();
            InputStream is = Histeria.getInstance().getResource("server.yml");
            Reader reader = new InputStreamReader(is);
            this.configuration.load(reader);

            this.password = this.configuration.getString("MySQL.password");
            this.userName = this.configuration.getString("MySQL.user");
            this.host = this.configuration.getString("MySQL.host");
            this.port = this.configuration.getInt("MySQL.port");
            this.database = this.configuration.getString("MySQL.dataBase");


        } catch (InvalidConfigurationException | IOException e) {

            e.printStackTrace();

        }

    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }
}
