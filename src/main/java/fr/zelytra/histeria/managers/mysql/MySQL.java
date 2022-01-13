/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.mysql;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {
    private Connection connection;
    private Statement statement;
    private Object synchroObject = new Object();
    private boolean isConnected = false;

    /**
     * MySQL api
     */

    public MySQL() {
        connect();
    }

    public boolean isConnected(){
        return isConnected;
    }

    private void connect() {
        try {
            SQLConfiguration configuration = new SQLConfiguration();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + configuration.getHost()
                            + ":" + configuration.getPort()
                            + "/" + configuration.getDatabase()
                    , configuration.getUserName()
                    , configuration.getPassword());
            this.statement = this.connection.createStatement();
            this.isConnected = true;
            Histeria.log("Connected to dataBase !", LogType.INFO);
        } catch (SQLException exception) {
            Histeria.log("Failed to connect to DB, shutting down server", LogType.ERROR);
            this.isConnected = false;
            Bukkit.shutdown();
        }
    }

    public void update(String sql) {

        try {
            synchronized (synchroObject) {
                checkConnection();
                statement.executeUpdate(sql);
            }
        } catch (SQLException ignored) {
        }

    }

    private void checkConnection() {
        try {

            if (connection != null && !connection.isValid(2)) {
                Histeria.log("Lost connection to DB, try to reconnecting", LogType.WARN);
                this.isConnected = false;
                connect();
            }

        } catch (SQLException ignored) {
        }

    }

    public ResultSet query(String sql) {

        try {
            synchronized (synchroObject) {
                checkConnection();
                return statement.executeQuery(sql);
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
