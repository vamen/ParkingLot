package in.vivek.parking.feestrategies.model;

import in.vivek.parking.feestrategy.model.FeeStructure;
import lombok.Data;
import lombok.Getter;


@Data
public class RailWayFeeStructure extends FeeStructure {
      private  Double weekDayPerHourFee;

      private Double weekEndPerHourFee;

}
