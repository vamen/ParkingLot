package in.vivek.parking.feestrategies.model;


import lombok.Data;


@Data
public class RangeFee {
    //TODO: Ideally the range type should be Instant or LocalTime
    public Integer fromTime;
    public Integer toTime;
    public double fee;
}


