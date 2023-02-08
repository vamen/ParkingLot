package in.vivek.parking.feestrategies;

import com.fasterxml.jackson.core.type.TypeReference;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.feestrategies.model.AirportFeeStructure;
import in.vivek.parking.feestrategy.model.FeeModel;
import in.vivek.parking.model.vehicle.SpotType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;

import static in.vivek.parking.TestUtil.objectMapper;
import static in.vivek.parking.TestUtil.readFileFromResources;

class AirportFeeStrategyTest {

    @Test
    void init() {
    }

    @Test
    void computeFee() throws IOException, URISyntaxException {

        AirportFeeStrategy airportFeeStrategy = new AirportFeeStrategy();
        FeeModel<AirportFeeStructure> feeModel = objectMapper.readValue(readFileFromResources("airport_fee_model.json"), new TypeReference<FeeModel<AirportFeeStructure>>() {
        });
        FeeModelDao<AirportFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        airportFeeStrategy.init(feeModelDao);
        Instant inTime = Instant.now();
        Assertions.assertEquals(0.0, airportFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(55))));
        Assertions.assertEquals(60, airportFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(14 * 60 + 59))));
        Assertions.assertEquals(160, airportFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(24 * 60 + 12 * 60))));
        Assertions.assertEquals(60, airportFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(50))));
        Assertions.assertEquals(80, airportFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(23 * 60 + 59))));
        Assertions.assertEquals(400, airportFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(3 * 24 * 60 + 60))));

    }
}