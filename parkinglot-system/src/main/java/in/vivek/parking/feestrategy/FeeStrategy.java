package in.vivek.parking.feestrategy;

import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.feestrategy.model.FeeStructure;
import in.vivek.parking.model.vehicle.SpotType;

import java.time.Instant;

public interface FeeStrategy<T extends FeeStructure> {

    public void init(FeeModelDao<T> feeModelDao);

    public double computeFee(SpotType spotType, Instant inTime, Instant outTime);

}
