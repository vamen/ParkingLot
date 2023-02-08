package in.vivek.parking.dao;

import in.vivek.parking.model.tickets.Ticket;

public interface TicketDao<T extends Ticket> {

    public T getTicket(Long ticketId);

    public Long saveTicket(T Ticket);
}
