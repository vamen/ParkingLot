package in.vivek.parking.exception;

public class InvalidSpotIdException extends RuntimeException {
    String message;

    public InvalidSpotIdException(Long id) {
        this.message = String.format(" passed spot id %s is invalid ", id);
    }
}
