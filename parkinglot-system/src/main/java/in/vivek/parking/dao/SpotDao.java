package in.vivek.parking.dao;

import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.spots.Spot;

import java.util.Optional;

public interface SpotDao<T extends Spot> {

    public Optional<T> findAndAllotSpot(T spot);

    public void unAllotSpot(Long spot);

    public SingleSpot getSpot(Long id);

}
