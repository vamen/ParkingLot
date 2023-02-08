package in.vivek.parking.test.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import in.vivek.parking.SimpleParkingLot;
import in.vivek.parking.dao.InMemorySingleSpotDao;
import in.vivek.parking.dao.InMemoryTicketDao;
import in.vivek.parking.dao.SpotDao;
import in.vivek.parking.dao.TicketDao;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.tickets.Ticket;
import in.vivek.parking.model.vehicle.SpotType;
import in.vivek.parking.service.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class ParkingLotTest {

    SimpleParkingLot simpleParkingLot;

    ObjectMapper objectMapper = new ObjectMapper();

    void initialize(ImmutableMap<SpotType, Integer> spotAvailability, FeeStrategy feeStrategy) throws IOException, URISyntaxException {
        objectMapper = new ObjectMapper();
        SingleSpotAllocatorService spotAllocatorService = initializeSpotAllocatorService(spotAvailability);

        // initialize ticket service
        TicketingService ticketingService = initializeTicketingService();

        // initialize fee calculator service
        FeeCalculatorService<SingleSpot> feeCalculatorService = initializeSingleSpotFeeCalculatorService(spotAvailability, feeStrategy);

        ReceiptGeneratorService<Receipt> receiptGeneratorService = new SimpleReceiptGeneratorService();

        simpleParkingLot = new SimpleParkingLot(spotAllocatorService, ticketingService, feeCalculatorService, receiptGeneratorService);

    }

    private FeeCalculatorService<SingleSpot> initializeSingleSpotFeeCalculatorService(ImmutableMap<SpotType, Integer> spotAvailability, FeeStrategy feeStrategy) throws IOException, URISyntaxException {
        SpotDao<SingleSpot> spotDao = new InMemorySingleSpotDao(spotAvailability);
        FeeCalculatorService<SingleSpot> feeCalculatorService = new SingleSpotFeeCalculatorService();
        feeCalculatorService.init(feeStrategy, spotDao);
        return feeCalculatorService;
    }


    private TicketingService initializeTicketingService() {
        TicketDao<Ticket> ticketDao = new InMemoryTicketDao();
        TicketingService ticketingService = new SimpleTicketService();
        ticketingService.init(ticketDao);
        return ticketingService;
    }

    private SingleSpotAllocatorService initializeSpotAllocatorService(Map<SpotType, Integer> spotValues) {
        SpotDao<SingleSpot> spotDao = new InMemorySingleSpotDao(spotValues);
        SingleSpotAllocatorService singleSpotAllocatorService = new SingleSpotAllocatorService();
        singleSpotAllocatorService.init(spotDao);
        return singleSpotAllocatorService;
    }
}
