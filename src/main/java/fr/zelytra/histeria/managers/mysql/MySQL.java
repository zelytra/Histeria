package fr.zelytra.histeria.managers.mysql;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.logs.LogType;

import java.sql.*;

public class MySQL {
    private Connection connection;
    private Statement statement;
    private Object synchroObject = new Object();

    public MySQL() {

        try {

            SQLConfiguration configuration = new SQLConfiguration();

            this.connection = DriverManager.getConnection("jdbc:mysql://" + configuration.getHost()
                            + ":" + configuration.getPort()
                            + "/" + configuration.getDatabase()
                    , configuration.getUserName()
                    , configuration.getPassword());
            this.statement = this.connection.createStatement();
            Histeria.log("Connected to dataBase !", LogType.INFO);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void update(String query) {

        try {
            synchronized (synchroObject) {
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet query(String query) {

        try {
            synchronized (synchroObject) {
                return statement.executeQuery(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
