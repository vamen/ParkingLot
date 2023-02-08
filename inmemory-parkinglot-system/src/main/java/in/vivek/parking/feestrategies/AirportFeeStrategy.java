package in.vivek.parking.feestrategies;

import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.exception.InvalidSpotTypeException;
import in.vivek.parking.feestrategies.model.AirportFeeStructure;
import in.vivek.parking.feestrategies.model.RangeFee;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.vehicle.SpotType;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AirportFeeStrategy implements FeeStrategy<AirportFeeStructure> {

    private FeeModelDao<AirportFeeStructure> airportFeeModelDao;

    @Override
    public void init(FeeModelDao<AirportFeeStructure> feeModelDao) {
        this.airportFeeModelDao = feeModelDao;
    }

    @Override
    public double computeFee(SpotType spotType, Instant inTime, Instant outTime) {
        Duration duration = Duration.between(inTime, outTime);

        if (airportFeeModelDao.containsSpotType(spotType)) {
            AirportFeeStructure spotFeeStructure = airportFeeModelDao.getFeeStructure(spotType);
            if (duration.toDays() >= 1) {
                return Math.ceil(duration.toHours() / 24.0) * spotFeeStructure.getPerDayFee();
            } else {
                List<RangeFee> sortedFee = spotFeeStructure.getRangeFees().stream().sorted(Comparator.comparing((RangeFee::getToTime))).collect(Collectors.toList());
                for (RangeFee range : sortedFee) {
                    if (duration.toMinutes() < range.getToTime() * 60) {
                        return range.getFee();
                    }
                }
            }
        } else {
            throw new InvalidSpotTypeException(spotType);
        }
        return 0;
    }
}
