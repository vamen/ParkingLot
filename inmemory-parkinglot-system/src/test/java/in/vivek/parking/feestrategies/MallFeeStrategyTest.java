package in.vivek.parking.feestrategies;

import com.fasterxml.jackson.core.type.TypeReference;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.feestrategies.model.MallFeeStructure;
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

class MallFeeStrategyTest {

    @Test
    void init() {
    }

    @Test
    void computeFee() throws IOException, URISyntaxException {

        MallFeeStrategy mallFeeStrategy = new MallFeeStrategy();
        FeeModel<MallFeeStructure> feeModel = objectMapper.readValue(readFileFromResources("mall_fee_model.json"), new TypeReference<FeeModel<MallFeeStructure>>() {
        });
        FeeModelDao<MallFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        mallFeeStrategy.init(feeModelDao);
        Instant inTime = Instant.now();
        Assertions.assertEquals(0, mallFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(0))));
        Assertions.assertEquals(10, mallFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(60))));
        Assertions.assertEquals(20, mallFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(61))));
        Assertions.assertEquals(20, mallFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(119))));
        Assertions.assertEquals(20, mallFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime, inTime.plus(Duration.ofMinutes(119))));
        Assertions.assertEquals(60, mallFeeStrategy.computeFee(SpotType.SMALL_FOUR_WHEEL, inTime, inTime.plus(Duration.ofMinutes(120))));

    }
}