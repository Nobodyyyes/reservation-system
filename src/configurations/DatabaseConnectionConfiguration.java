package configurations;

import exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionConfiguration {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/reservation";
    private static final String DB_USERNAME = "reservation";
    private static final String DB_PASSWORD = "qazwsxedc";

    private static Connection connection;

    private DatabaseConnectionConfiguration() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Failed to connect to database", sqle);
        }
        return connection;
    }
}
