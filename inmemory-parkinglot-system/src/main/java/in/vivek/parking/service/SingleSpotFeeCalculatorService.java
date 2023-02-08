package in.vivek.parking.service;

import in.vivek.parking.dao.SpotDao;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.tickets.Ticket;

import java.time.Instant;

public class SingleSpotFeeCalculatorService implements FeeCalculatorService<SingleSpot> {
    private FeeStrategy feeStrategy;
    private SpotDao<SingleSpot> spotDao;

    @Override
    public void init(FeeStrategy feeStrategy, SpotDao<SingleSpot> spotDao) {
        this.feeStrategy = feeStrategy;
        this.spotDao = spotDao;
    }

    @Override
    public double calculateFee(Ticket ticket, Instant outTime) {

        SingleSpot spot = spotDao.getSpot(ticket.getSpotId());
        return this.feeStrategy.computeFee(spot.spotType(), ticket.getInTime(), outTime);

    }
}
