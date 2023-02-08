package in.vivek.parking.feestrategies;


import com.fasterxml.jackson.core.type.TypeReference;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.feestrategies.model.StadiumFeeStructure;
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

class StadiumFeeStrategyTest {

    @Test
    void testComputeFee() throws IOException, URISyntaxException {


        StadiumFeeStrategy stadiumFeeStrategy = new StadiumFeeStrategy();
        FeeModel<StadiumFeeStructure> feeModel = objectMapper.readValue(readFileFromResources("stadium_fee_model.json"), new TypeReference<FeeModel<StadiumFeeStructure>>() {
        });
        FeeModelDao<StadiumFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        stadiumFeeStrategy.init(feeModelDao);
        Instant inTime = Instant.now();
        Assertions.assertEquals(30, stadiumFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(220))));
        Assertions.assertEquals(390, stadiumFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(899))));
        Assertions.assertEquals(180, stadiumFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(699))));
        Assertions.assertEquals(580, stadiumFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(785))));

    }
}