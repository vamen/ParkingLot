package in.vivek.parking.service;

import in.vivek.parking.dao.SpotDao;
import in.vivek.parking.exception.NoMoreSpotException;
import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.vehicle.Vehicle;
import in.vivek.parking.model.vehicle.VehicleToSpotTypeConvertor;

import java.util.Optional;

public class SingleSpotAllocatorService implements SpotAllocatorService<SingleSpot> {


    private SpotDao<SingleSpot> spotDao;

    @Override
    public void init(SpotDao<SingleSpot> spotDao) {
        this.spotDao = spotDao;
    }

    @Override
    public Long allotSpot(Vehicle vehicle) throws NoMoreSpotException {
        SingleSpot spot = SingleSpot.builder().spotType(VehicleToSpotTypeConvertor.getInstance().getSpotType(vehicle)).build();
        Optional<SingleSpot> allottedSpot = spotDao.findAndAllotSpot(spot);
        if (allottedSpot.isEmpty()) {
            throw new NoMoreSpotException(" No space available ");
        }
        return allottedSpot.get().spotId();
    }

    @Override
    public void unAllotSpot(Long spotId) {
        spotDao.unAllotSpot(spotId);
    }
}
