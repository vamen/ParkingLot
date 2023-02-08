package in.vivek.parking.dao;

import in.vivek.parking.feestrategy.model.FeeStructure;
import in.vivek.parking.model.vehicle.SpotType;

public interface FeeModelDao<T extends FeeStructure> {

    T getFeeStructure(SpotType spotType);

    boolean containsSpotType(SpotType spotType);

}
