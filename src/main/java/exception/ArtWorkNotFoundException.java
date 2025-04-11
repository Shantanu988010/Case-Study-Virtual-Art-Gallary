
package exception;

import util.HexaConstants;

public class ArtWorkNotFoundException extends Exception {
    public ArtWorkNotFoundException() {
        super();
    }



    public ArtWorkNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return HexaConstants.ARTWORK_NOT_FOUND;
    }
}

