package in.vivek.parking.exception;

import in.vivek.parking.model.vehicle.SpotType;

public class VehicleTypeNotAllowed extends Exception {
    String message;

    public VehicleTypeNotAllowed(SpotType spotType) {
        this.message = String.format(" vehicle type %s not allowed ", spotType.name());
    }
}
