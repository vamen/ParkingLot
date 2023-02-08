package in.vivek.parking.exception;

import in.vivek.parking.model.vehicle.SpotType;

public class InvalidSpotTypeException extends RuntimeException {

    String message;

    public InvalidSpotTypeException(SpotType spotType) {
        this.message = String.format(" spotType type %s not allowed ", spotType.name());
    }
}
