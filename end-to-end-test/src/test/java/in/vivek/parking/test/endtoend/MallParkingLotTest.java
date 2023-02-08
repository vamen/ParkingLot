package in.vivek.parking.test.endtoend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.exception.NoMoreSpotException;
import in.vivek.parking.feestrategies.MallFeeStrategy;
import in.vivek.parking.feestrategies.model.MallFeeStructure;
import in.vivek.parking.feestrategy.FeeStrategy;
import in.vivek.parking.feestrategy.model.FeeModel;
import in.vivek.parking.model.MotorCycle;
import in.vivek.parking.model.Receipt;
import in.vivek.parking.model.Scooter;
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


public class MallParkingLotTest extends ParkingLotTest {


    @BeforeEach
    public void setup() throws IOException, URISyntaxException {


        ImmutableMap<SpotType, Integer> spotAvailability = ImmutableMap.of(
                SpotType.TWO_WHEEL, 2
        );
        // initialize parkingLot

        FeeModel<MallFeeStructure> feeModel = objectMapper.readValue(TestUtil.readFileFromResources("mall_fee_model.json"), new TypeReference<FeeModel<MallFeeStructure>>() {
        });
        FeeModelDao<MallFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        FeeStrategy<MallFeeStructure> feeStrategy = new MallFeeStrategy();
        feeStrategy.init(feeModelDao);
        this.initialize(spotAvailability, feeStrategy);

    }


    @Test
    public void mallParkingLotTest() {

        try {



            Vehicle vehicle = new MotorCycle();

            Instant inTime1 = Instant.now();
            Ticket ticket1 = simpleParkingLot.parkVehicle(vehicle, inTime1);
            Assertions.assertTrue(inTime1.equals(ticket1.getInTime()));
            System.out.println(ticket1.toString());

            vehicle = new Scooter();
            Instant inTime2 = Instant.now();
            Ticket ticket2 = simpleParkingLot.parkVehicle(vehicle, inTime2);
            Assertions.assertTrue(inTime2.equals(ticket2.getInTime()));
            System.out.println(ticket2.toString());


            vehicle = new Scooter();
            Instant inTime3 = Instant.now();
            Vehicle finalVehicle = vehicle;
            Assertions.assertThrows(NoMoreSpotException.class, () -> {
                simpleParkingLot.parkVehicle(finalVehicle, inTime3);
            });

            Instant outTime1 = inTime2.plus(Duration.ofMinutes(54));
            Receipt receipt1 = simpleParkingLot.unParkVehicle(ticket2.getTicketId(), outTime1);
            Assertions.assertEquals(10, receipt1.getFees());
            System.out.println(receipt1.toString());

            vehicle = new MotorCycle();
            Instant inTime4 = Instant.now();
            Ticket ticket3 = simpleParkingLot.parkVehicle(vehicle, inTime4);
            Assertions.assertTrue(inTime4.equals(ticket3.getInTime()));
            System.out.println(ticket3.toString());

            Instant outTime2 = inTime1.plus(Duration.ofMinutes(60 * 3 + 44));
            Receipt receipt2 = simpleParkingLot.unParkVehicle(ticket2.getTicketId(), outTime2);
            Assertions.assertEquals(40, receipt2.getFees());
            System.out.println(receipt2.toString());


        } catch (Exception ex) {
            ex.printStackTrace();
            assert false;
        }

    }


}
