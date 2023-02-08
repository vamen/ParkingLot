package in.vivek.parking.service;

import in.vivek.parking.dao.TicketDao;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public interface TicketingService {

    public void init(TicketDao dao);

    public Long generateTicket(Long spotId, Instant inTime);

    public Ticket getTicket(Long ticketId);

}
