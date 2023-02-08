package in.vivek.parking.service;

import in.vivek.parking.dao.TicketDao;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public class SimpleTicketService implements TicketingService {
    private TicketDao ticketDao;

    @Override
    public void init(TicketDao dao) {
        this.ticketDao = dao;
    }

    @Override
    public Long generateTicket(Long spotId, Instant inTime) {
        Ticket ticket = Ticket.builder().spotId(spotId).inTime(inTime).build();
        return ticketDao.saveTicket(ticket);

    }

    @Override
    public Ticket getTicket(Long ticketId) {
        Ticket ticket = ticketDao.getTicket(ticketId);
        return ticket;
    }
}
