package configurations;

import exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionConfiguration {

    private static final String DB_URL = "jdbc:postgresql://localhost:5433/reservation";
    private static final String DB_USERNAME = "reservation";
    private static final String DB_PASSWORD = "qazwsxedc";

    private DatabaseConnectionConfiguration() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Failed to connect to database", sqle);
        }
    }
}
