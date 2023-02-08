package in.vivek.parking.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Receipt {

    String receiptId;
    Long spotId;
    Instant inTime;
    Instant outTime;
    double fees;

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", sotId=" + spotId +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", fees=" + fees +
                '}';
    }
}
