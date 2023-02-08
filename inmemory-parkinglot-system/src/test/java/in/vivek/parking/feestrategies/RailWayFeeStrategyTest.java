package in.vivek.parking.feestrategies;

import com.fasterxml.jackson.core.type.TypeReference;
import in.vivek.parking.TestUtil;
import in.vivek.parking.dao.FeeModelDao;
import in.vivek.parking.dao.InMemoryFeeModelDao;
import in.vivek.parking.feestrategies.model.MallFeeStructure;
import in.vivek.parking.feestrategies.model.RailWayFeeStructure;
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
import static org.junit.jupiter.api.Assertions.*;

class RailWayFeeStrategyTest {

    @Test
    void computeFee() throws IOException, URISyntaxException {

        FeeModel<RailWayFeeStructure> feeModel = objectMapper.readValue(TestUtil.readFileFromResources("railway_feemodel.json"), new TypeReference<FeeModel<RailWayFeeStructure>>() {
        });
        FeeModelDao<RailWayFeeStructure> feeModelDao = new InMemoryFeeModelDao<>(feeModel);
        RailWayFeeStrategy railWayFeeStrategy = new RailWayFeeStrategy();

        railWayFeeStrategy.init(feeModelDao);

        Instant inTime = Instant.now();
        Instant outTime= inTime.plus(Duration.ofMinutes(90));
        Assertions.assertEquals(20,railWayFeeStrategy.computeFee(SpotType.TWO_WHEEL, inTime,outTime));

        inTime = Instant.parse("2023-02-04T10:00:00.00Z");
        outTime = Instant.parse("2023-02-08T9:30:30.00Z");

    }
}