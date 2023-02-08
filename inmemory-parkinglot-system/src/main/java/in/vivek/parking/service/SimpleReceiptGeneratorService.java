package in.vivek.parking.service;

import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public class SimpleReceiptGeneratorService implements ReceiptGeneratorService<Receipt> {

    private static Long seqNumber = 0L;

    private static Long getNextLong() {
        synchronized (Ticket.class) {
            return ++seqNumber;
        }
    }

    @Override
    public Receipt generateReceipt(Ticket ticket, Instant endTime, double fees) {

        Receipt receipt = Receipt.builder().
                receiptId(String.format("R-%03d", getNextLong()))
                .spotId(ticket.getSpotId())
                .inTime(ticket.getInTime())
                .outTime(endTime)
                .fees(fees)
                .build();

        return receipt;
    }
}
