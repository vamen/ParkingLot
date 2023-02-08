package in.vivek.parking.dao;

import in.vivek.parking.exception.InvalidTicketException;
import in.vivek.parking.model.tickets.Ticket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryTicketDao implements TicketDao<Ticket> {

    private static Long seqNumber = 0L;
    private Map<Long, Ticket> tickets = new ConcurrentHashMap<>();

    private static Long getNextLong() {
        synchronized (Ticket.class) {
            return ++seqNumber;
        }
    }

    @Override
    public Ticket getTicket(Long ticketId) {

        if (tickets.containsKey(ticketId)) {
            return tickets.get(ticketId);
        } else {
            throw new InvalidTicketException(Ticket.builder().ticketId(ticketId).build());
        }
    }

    @Override
    public Long saveTicket(Ticket ticket) {
        ticket.setTicketId(getNextLong());
        this.tickets.put(ticket.getTicketId(), ticket);
        return ticket.getTicketId();

    }
}
