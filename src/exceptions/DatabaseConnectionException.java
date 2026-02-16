package exceptions;

import java.sql.SQLException;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message, SQLException sqle) {
        super(message);
    }
}
