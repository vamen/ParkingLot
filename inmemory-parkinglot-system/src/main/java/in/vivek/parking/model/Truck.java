package in.vivek.parking.model;

import in.vivek.parking.model.vehicle.Vehicle;
import in.vivek.parking.model.vehicle.VehicleType;

public class Truck implements Vehicle {
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.LARGE_FOUR_WHEEL;
    }
}
