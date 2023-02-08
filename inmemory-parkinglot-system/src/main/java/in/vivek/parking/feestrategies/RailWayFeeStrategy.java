package in.vivek.parking.feestrategies;

import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.feestrategies.model.MallFeeStructure;
import in.vivek.parking.feestrategies.model.RailWayFeeStructure;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.vehicle.SpotType;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class RailWayFeeStrategy implements FeeStrategy<RailWayFeeStructure> {
    private FeeModelDao<RailWayFeeStructure> feeModelDao;

    @Override
    public void init(FeeModelDao<RailWayFeeStructure> feeModelDao) {
        this.feeModelDao = feeModelDao;
    }

    public boolean isWeekEnd(LocalDateTime localDateTime){
        switch (localDateTime.getDayOfWeek()){
            case SUNDAY:
            case SATURDAY:
                return true;
            default:
                return false;
        }
    }

    public Double getPricePerHour(RailWayFeeStructure railWayFeeStructure,LocalDateTime localDateTime){
        if(isWeekEnd(localDateTime)){
            return  railWayFeeStructure.getWeekEndPerHourFee();
        }else{
            return  railWayFeeStructure.getWeekDayPerHourFee();
        }
    }
    @Override
    public double computeFee(SpotType spotType, Instant inTime, Instant outTime) {
        LocalDateTime inDateTime = LocalDateTime.ofInstant(inTime, ZoneOffset.ofHoursMinutes(5,30));
        LocalDateTime outDateTime = LocalDateTime.ofInstant(outTime, ZoneOffset.ofHoursMinutes(5,30));
        RailWayFeeStructure railWayFeeStructure = this.feeModelDao.getFeeStructure(spotType);
        if(inDateTime.getYear() == outDateTime.getYear() && inDateTime.getDayOfYear()==outDateTime.getDayOfYear()){

            Duration duration = Duration.between(inDateTime,outDateTime);
           Double totalHour = Math.ceil(duration.toMinutes() / 60.0);
            if(isWeekEnd(inDateTime)){

                return railWayFeeStructure.getWeekEndPerHourFee() * totalHour;
            }else{
                return railWayFeeStructure.getWeekDayPerHourFee() * totalHour;
            }
        }else{

            get
            Long starDayDuration = Duration.between(inDateTime,);
            int endTime = outDateTime.getMinute() > 0 ? outDateTime.getHour() +1: outDateTime.getHour();
            Double inDayFee = getPricePerHour (railWayFeeStructure,inDateTime) * (24-startTime);
            Double outDayFee = getPricePerHour(railWayFeeStructure,outDateTime) * (endTime) ;

            if(inDateTime.getYear() == outDateTime.getYear() && inDateTime.getDayOfYear()+1==outDateTime.getDayOfYear()){
                    return inDayFee+outDayFee;
            }else{
                   Double inBetweenDayFees=0.0;
                   LocalDateTime inBetweenDay = inDateTime.plusDays(1);
                   while(inBetweenDay.compareTo(outDateTime)<-1){
                       inBetweenDayFees+=getPricePerHour(railWayFeeStructure,inBetweenDay) * 24;
                   }
                   return inBetweenDayFees;
            }
        }

    }
}
