package in.vivek.parking.model;

import in.vivek.parking.model.vehicle.Vehicle;
import in.vivek.parking.model.vehicle.VehicleType;

public class Car implements Vehicle {
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.SMALL_FOUR_WHEEL;
    }
}
