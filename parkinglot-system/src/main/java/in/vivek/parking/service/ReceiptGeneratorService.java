package in.vivek.parking.service;

import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public interface ReceiptGeneratorService<T extends Receipt> {

    public T generateReceipt(Ticket ticket, Instant endTime, double fees);
}
