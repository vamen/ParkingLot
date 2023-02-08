package in.vivek.parking.exception;

public class NoMoreSpotException extends Exception {

    String message;

    public NoMoreSpotException(String message) {
        this.message = message;
    }
}
