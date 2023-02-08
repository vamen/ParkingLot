package in.vivek.parking.service;

import in.vivek.parking.dao.SpotDao;
import in.vivek.parking.exception.NoMoreSpotException;
import in.vivek.parking.model.spots.Spot;
import in.vivek.parking.model.vehicle.Vehicle;

public interface SpotAllocatorService<T extends Spot> {


    /**
     * method to initialize spot availability
     */
    public void init(SpotDao<T> spotDao);

    public Long allotSpot(Vehicle vehicle) throws NoMoreSpotException;

    public void unAllotSpot(Long spotId);
}
