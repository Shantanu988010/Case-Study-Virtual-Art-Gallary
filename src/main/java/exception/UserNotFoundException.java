package exception;

import util.HexaConstants;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return HexaConstants.USER_NOT_FOUND;
    }
}