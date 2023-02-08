package in.vivek.parking.feestrategies.model;

import in.vivek.parking.feestrategy.model.FeeStructure;
import lombok.Data;

import java.util.List;

@Data
public class AirportFeeStructure extends FeeStructure {
    private List<RangeFee> rangeFees;
    private Double perDayFee;

}
