package in.vivek.parking.exception;

import in.vivek.parking.model.tickets.Ticket;

public class InvalidTicketException extends RuntimeException {
    String message;

    public InvalidTicketException(Ticket ticket) {

    }
}
