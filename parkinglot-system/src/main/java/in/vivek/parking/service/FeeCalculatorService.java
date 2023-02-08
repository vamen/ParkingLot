package in.vivek.parking.service;

import in.vivek.parking.dao.SpotDao;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.spots.Spot;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public interface FeeCalculatorService<T extends Spot> {

    public void init(FeeStrategy feeStrategy, SpotDao<T> spotDao);

    public double calculateFee(Ticket ticket, Instant outTime);

}
