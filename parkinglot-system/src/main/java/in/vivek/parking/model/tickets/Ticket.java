package in.vivek.parking.model.tickets;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class Ticket {

    private Long ticketId;
    private Long spotId;
    private Instant inTime;


    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + String.format("%03d", ticketId) +
                ", spotId=" + spotId +
                ", inTime=" + inTime +
                '}';
    }
}
