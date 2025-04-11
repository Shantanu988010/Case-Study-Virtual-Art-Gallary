package exception;

import util.HexaConstants;

public class DbConnectionException extends Exception{

    public DbConnectionException() {
    }

    @Override
    public String toString() {
        return HexaConstants.CANNOT_OPEN_CONNECTION;
    }

    public DbConnectionException(String message) {
        super(message);
    }

    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbConnectionException(Throwable cause) {
        super(cause);
    }

    public DbConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
