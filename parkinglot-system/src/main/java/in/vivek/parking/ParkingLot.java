package in.vivek.parking;


import in.vivek.parking.exception.NoMoreSpotException;
import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.tickets.Ticket;
import in.vivek.parking.model.vehicle.Vehicle;

import java.time.Instant;

public interface ParkingLot {

    Ticket parkVehicle(Vehicle vehicle, Instant inTime) throws NoMoreSpotException;

    Receipt unParkVehicle(Long id, Instant outTime);

}
