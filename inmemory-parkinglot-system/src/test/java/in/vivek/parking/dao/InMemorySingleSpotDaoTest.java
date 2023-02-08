package in.vivek.parking.dao;

import in.vivek.parking.exception.InvalidSpotIdException;
import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.vehicle.SpotType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class InMemorySingleSpotDaoTest {

    /**
     * test parking lot with no spots
     */
    @Test
    void testNoSpot() {

        InMemorySingleSpotDao inMemorySingleSpotDao = new InMemorySingleSpotDao(Map.of());
        Assertions.assertTrue(inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.TWO_WHEEL).build()).isEmpty());
        InMemorySingleSpotDao finalInMemorySingleSpotDao = inMemorySingleSpotDao;
        Assertions.assertThrows(InvalidSpotIdException.class, () ->
                finalInMemorySingleSpotDao.unAllotSpot(1L));
        inMemorySingleSpotDao = new InMemorySingleSpotDao(Map.of(
                SpotType.TWO_WHEEL, 0,
                SpotType.SMALL_FOUR_WHEEL, 0,
                SpotType.LARGE_FOUR_WHEEL, 0
        ));

        Assertions.assertTrue(inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.TWO_WHEEL).build()).isEmpty());
        InMemorySingleSpotDao finalInMemorySingleSpotDao1 = inMemorySingleSpotDao;
        Assertions.assertThrows(InvalidSpotIdException.class, () ->
                finalInMemorySingleSpotDao1.unAllotSpot(1L));

    }

    /**
     * test parking lot  spot intialization
     */
    @Test
    void testSpotInitialization() throws NoSuchFieldException, IllegalAccessException {

        InMemorySingleSpotDao inMemorySingleSpotDao = new InMemorySingleSpotDao(Map.of(
                SpotType.TWO_WHEEL, 1,
                SpotType.SMALL_FOUR_WHEEL, 1,
                SpotType.LARGE_FOUR_WHEEL, 0
        ));

        Field field = inMemorySingleSpotDao.getClass().getDeclaredField("unAllottedSpots");
        field.setAccessible(true);
        Map<SpotType, LinkedList<SingleSpot>> unAllottedSpots = (Map<SpotType, LinkedList<SingleSpot>>) field.get(inMemorySingleSpotDao);
        ;
        Assertions.assertTrue(unAllottedSpots.size() == 3);
        Assertions.assertTrue(unAllottedSpots.get(SpotType.TWO_WHEEL).size() == 1);
        Assertions.assertTrue(unAllottedSpots.get(SpotType.SMALL_FOUR_WHEEL).size() == 1);
        Assertions.assertTrue(unAllottedSpots.get(SpotType.LARGE_FOUR_WHEEL).size() == 0);


        field = inMemorySingleSpotDao.getClass().getDeclaredField("allottedSpots");
        field.setAccessible(true);
        Map<SpotType, Map<Long, SingleSpot>> allottedSpots = (Map<SpotType, Map<Long, SingleSpot>>) field.get(inMemorySingleSpotDao);
        Assertions.assertTrue(allottedSpots.size() == 3);
        Assertions.assertTrue(allottedSpots.get(SpotType.TWO_WHEEL).size() == 0);
        Assertions.assertTrue(allottedSpots.get(SpotType.SMALL_FOUR_WHEEL).size() == 0);
        Assertions.assertTrue(allottedSpots.get(SpotType.LARGE_FOUR_WHEEL).size() == 0);

        field = inMemorySingleSpotDao.getClass().getDeclaredField("spotIdToSpotMap");
        field.setAccessible(true);
        Map<Long, SingleSpot> spotIdToSpotMap = (Map<Long, SingleSpot>) field.get(inMemorySingleSpotDao);
        Assertions.assertTrue(spotIdToSpotMap.size() == 2);
        Assertions.assertTrue(spotIdToSpotMap.keySet().containsAll(Set.of(0L, 1L)));
        Assertions.assertTrue(
                (spotIdToSpotMap.get(0L).spotType().equals(SpotType.TWO_WHEEL) && spotIdToSpotMap.get(1L).spotType().equals(SpotType.SMALL_FOUR_WHEEL))
                        || (spotIdToSpotMap.get(1L).spotType().equals(SpotType.TWO_WHEEL) && spotIdToSpotMap.get(0L).spotType().equals(SpotType.SMALL_FOUR_WHEEL)));

    }


    /*
     *   test internal state changes during the process of allocation and de allocation
     */

    @Test
    public void testAllotAnUnAllot() throws NoSuchFieldException, IllegalAccessException {

        InMemorySingleSpotDao inMemorySingleSpotDao = new InMemorySingleSpotDao(Map.of(
                SpotType.TWO_WHEEL, 2,
                SpotType.SMALL_FOUR_WHEEL, 1,
                SpotType.LARGE_FOUR_WHEEL, 0
        ));
        Field field = inMemorySingleSpotDao.getClass().getDeclaredField("unAllottedSpots");
        field.setAccessible(true);
        Map<SpotType, LinkedList<SingleSpot>> unAllottedSpots = (Map<SpotType, LinkedList<SingleSpot>>) field.get(inMemorySingleSpotDao);
        ;

        field = inMemorySingleSpotDao.getClass().getDeclaredField("allottedSpots");
        field.setAccessible(true);
        Map<SpotType, Map<Long, SingleSpot>> allottedSpots = (Map<SpotType, Map<Long, SingleSpot>>) field.get(inMemorySingleSpotDao);

        Assertions.assertThrows(IllegalArgumentException.class, () -> inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().build()));
        Assertions.assertTrue(inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.LARGE_FOUR_WHEEL).build()).isEmpty());

        Optional<SingleSpot> spotId1 = inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.TWO_WHEEL).build());
        Assertions.assertTrue(unAllottedSpots.get(SpotType.TWO_WHEEL).size() == 1);
        Assertions.assertTrue(allottedSpots.get(SpotType.TWO_WHEEL).size() == 1);

        Optional<SingleSpot> sportId2 = inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.TWO_WHEEL).build());
        Assertions.assertTrue(unAllottedSpots.get(SpotType.TWO_WHEEL).size() == 0);
        Assertions.assertTrue(allottedSpots.get(SpotType.TWO_WHEEL).size() == 2);

        Assertions.assertTrue(inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.TWO_WHEEL).build()).isEmpty());

        inMemorySingleSpotDao.unAllotSpot(sportId2.get().spotId());
        Assertions.assertTrue(unAllottedSpots.get(SpotType.TWO_WHEEL).size() == 1);
        Assertions.assertTrue(allottedSpots.get(SpotType.TWO_WHEEL).size() == 1);

        inMemorySingleSpotDao.unAllotSpot(spotId1.get().spotId());
        Assertions.assertTrue(unAllottedSpots.get(SpotType.TWO_WHEEL).size() == 2);
        Assertions.assertTrue(allottedSpots.get(SpotType.TWO_WHEEL).size() == 0);

        Assertions.assertThrows(InvalidSpotIdException.class, () -> {
            inMemorySingleSpotDao.unAllotSpot(spotId1.get().spotId());
        });

        Assertions.assertTrue(() -> inMemorySingleSpotDao.findAndAllotSpot(SingleSpot.builder().spotType(SpotType.LARGE_FOUR_WHEEL).build()).isEmpty());


    }

}