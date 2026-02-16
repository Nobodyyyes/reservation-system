package exceptions;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message, Throwable throwable) {
        super(message);
    }
}
