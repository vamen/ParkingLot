package in.vivek.parking.feestrategies;

import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.exception.InvalidSpotTypeException;
import in.vivek.parking.feestrategies.model.MallFeeStructure;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.vehicle.SpotType;

import java.time.Duration;
import java.time.Instant;

public class MallFeeStrategy implements FeeStrategy<MallFeeStructure> {
    private FeeModelDao<MallFeeStructure> feeModelDao;

    @Override
    public void init(FeeModelDao<MallFeeStructure> feeModelDao) {
        this.feeModelDao = feeModelDao;
    }

    @Override
    public double computeFee(SpotType spotType, Instant inTime, Instant outTime) {

        if (this.feeModelDao.containsSpotType(spotType)) {
            MallFeeStructure fee = this.feeModelDao.getFeeStructure(spotType);
            double flatFee = fee.getFee();
            return Math.ceil(Duration.between(inTime, outTime).toMinutes() / 60.0) * flatFee;
        } else {
            throw new InvalidSpotTypeException(spotType);
        }

    }
}
