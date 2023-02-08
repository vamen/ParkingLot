package in.vivek.parking.feestrategies;

import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.exception.InvalidSpotTypeException;
import in.vivek.parking.feestrategies.model.RangeFee;
import in.vivek.parking.feestrategies.model.StadiumFeeStructure;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.vehicle.SpotType;
import lombok.val;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Collectors;

public class StadiumFeeStrategy implements FeeStrategy<StadiumFeeStructure> {
    private FeeModelDao<StadiumFeeStructure> stadiumFeeModelDao;

    @Override
    public void init(FeeModelDao<StadiumFeeStructure> feeModelDao) {
        //TODO: validate feeModel , to make sure there is no overlapping of time
        this.stadiumFeeModelDao = feeModelDao;
    }


    @Override
    public double computeFee(SpotType spotType, Instant inTime, Instant outTime) {


        if (stadiumFeeModelDao.containsSpotType(spotType)) {
            Duration duration = Duration.between(inTime, outTime);
            Long minutes = duration.toMinutes();
            Long remainingMinutes = minutes;
            Double fee = 0.0;

            StadiumFeeStructure stadiumFeeStructure = stadiumFeeModelDao.getFeeStructure(spotType);
            for (val range : stadiumFeeStructure.getRangeFees().stream().sorted(Comparator.comparing((RangeFee::getToTime))).collect(Collectors.toList())) {
                if (minutes >= range.getToTime() * 60L) {
                    fee += range.getFee();
                    remainingMinutes -= (range.getToTime() * 60 - range.getFromTime() * 60);
                } else {
                    fee += range.getFee();
                    remainingMinutes = 0L;
                    break;
                }
            }
            if (remainingMinutes > 0) {
                fee += Math.ceil(remainingMinutes / 60.0) * stadiumFeeStructure.getPerHourFee();
            }
            return fee;
        } else {
            throw new InvalidSpotTypeException(spotType);
        }

    }
}
