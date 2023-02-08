package in.vivek.parking.test.endtoend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.feestrategies.StadiumFeeStrategy;
import in.vivek.parking.feestrategies.model.StadiumFeeStructure;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.feestrategy.model.FeeModel;
import in.vivek.parking.model.Car;
import in.vivek.parking.model.MotorCycle;
import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.tickets.Ticket;
import in.vivek.parking.model.vehicle.SpotType;
import in.vivek.parking.model.vehicle.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;

public class StadiumParkingLotTest extends ParkingLotTest {

    @BeforeEach
    public void setup() throws IOException, URISyntaxException {


        ImmutableMap<SpotType, Integer> spotAvailability = ImmutableMap.of(
                SpotType.TWO_WHEEL, 1000,
                SpotType.SMALL_FOUR_WHEEL, 500
        );
        // initialize parkingLot

        FeeModel<StadiumFeeStructure> feeModel = objectMapper.readValue(TestUtil.readFileFromResources("stadium_fee_model.json"), new TypeReference<FeeModel<StadiumFeeStructure>>() {
        });
        FeeModelDao<StadiumFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        FeeStrategy<StadiumFeeStructure> feeStrategy = new StadiumFeeStrategy();
        feeStrategy.init(feeModelDao);
        this.initialize(spotAvailability, feeStrategy);

    }

    @Test
    public void airPortParkingLotTest() {

        try {

            Vehicle vehicle = new MotorCycle();

            Instant inTime1 = Instant.now();
            Ticket ticket1 = simpleParkingLot.parkVehicle(vehicle, inTime1);
            Assertions.assertTrue(inTime1.equals(ticket1.getInTime()));
            System.out.println(ticket1.toString());

            Instant outTime1 = inTime1.plus(Duration.ofMinutes(3 * 60 + 40));
            Receipt receipt1 = simpleParkingLot.unParkVehicle(ticket1.getTicketId(), outTime1);
            Assertions.assertEquals(30, receipt1.getFees());
            System.out.println(receipt1.toString());

            vehicle = new Car();
            Instant inTime2 = Instant.now();
            Ticket ticket2 = simpleParkingLot.parkVehicle(vehicle, inTime2);
            Assertions.assertTrue(inTime2.equals(ticket2.getInTime()));
            System.out.println(ticket2.toString());

            Instant outTime2 = inTime2.plus(Duration.ofMinutes(13 * 60 + 30));
            Receipt receipt2 = simpleParkingLot.unParkVehicle(ticket2.getTicketId(), outTime2);
            Assertions.assertEquals(580.0, receipt2.getFees());
            System.out.println(receipt2.toString());


        } catch (Exception ex) {
            ex.printStackTrace();
            assert false;
        }

    }


}
