package in.vivek.parking;

import com.google.common.base.Preconditions;
import in.vivek.parking.exception.NoMoreSpotException;
import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.tickets.Ticket;
import in.vivek.parking.model.vehicle.Vehicle;
import in.vivek.parking.service.FeeCalculatorService;
import in.vivek.parking.service.ReceiptGeneratorService;
import in.vivek.parking.service.SpotAllocatorService;
import in.vivek.parking.service.TicketingService;

import java.time.Instant;

public class SimpleParkingLot implements ParkingLot {

    private SpotAllocatorService spotAllocatorService;

    private TicketingService ticketingService;

    private FeeCalculatorService feeCalculatorService;

    private ReceiptGeneratorService receiptGeneratorService;

    public SimpleParkingLot(SpotAllocatorService spotAllocatorService, TicketingService ticketingService, FeeCalculatorService feeCalculatorService, ReceiptGeneratorService receiptGeneratorService) {
        this.spotAllocatorService = spotAllocatorService;
        this.ticketingService = ticketingService;
        this.feeCalculatorService = feeCalculatorService;
        this.receiptGeneratorService = receiptGeneratorService;
    }

    @Override
    public Ticket parkVehicle(Vehicle vehicle, Instant inTime) throws NoMoreSpotException {

        Preconditions.checkArgument(vehicle != null, " what are you trying to park ?");
        Preconditions.checkArgument(inTime != null, " in time cannot be null");

        Long spotId = this.spotAllocatorService.allotSpot(vehicle);
        Long ticketId = this.ticketingService.generateTicket(spotId, inTime);
        Ticket ticket = this.ticketingService.getTicket(ticketId);
        return ticket;

    }

    @Override
    public Receipt unParkVehicle(Long ticketId, Instant outTime) {
        Preconditions.checkArgument(ticketId != null, " invalid input , null ticket id");
        Preconditions.checkArgument(outTime != null, " out time cannot be null");

        Ticket ticket = this.ticketingService.getTicket(ticketId);
        Preconditions.checkArgument(ticket.getInTime().toEpochMilli() < outTime.toEpochMilli(), " invalid input unpark time  is older then park time");

        double fee = this.feeCalculatorService.calculateFee(ticket, outTime);
        this.spotAllocatorService.unAllotSpot(ticket.getSpotId());
        return this.receiptGeneratorService.generateReceipt(ticket, outTime, fee);

    }
}
