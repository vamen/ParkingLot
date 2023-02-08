package in.vivek.parking.dao;

import com.google.common.base.Preconditions;
import in.vivek.parking.exception.InvalidSpotIdException;
import in.vivek.parking.model.spots.SingleSpot;
import in.vivek.parking.model.vehicle.SpotType;
import lombok.val;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class InMemorySingleSpotDao implements SpotDao<SingleSpot> {

    private Map<SpotType, LinkedList<SingleSpot>> unAllottedSpots;
    private Map<SpotType, Map<Long, SingleSpot>> allottedSpots;
    private Map<Long, SingleSpot> spotIdToSpotMap;

    public InMemorySingleSpotDao(Map<SpotType, Integer> initSpots) {

        this.spotIdToSpotMap = new HashMap<>();
        this.allottedSpots = new HashMap<>();
        this.unAllottedSpots = new HashMap<>();
        Long seq = 0L;
        for (val spotType : initSpots.keySet()) {
            LinkedList<SingleSpot> spotQueue = new LinkedList<SingleSpot>();
            for (int i = 0; i < initSpots.get(spotType); i++) {
                SingleSpot spot = SingleSpot.builder().spotId(seq++).spotType(spotType).build();
                spotQueue.add(spot);
                this.spotIdToSpotMap.put(spot.spotId(), spot);
            }
            this.unAllottedSpots.put(spotType, spotQueue);
            this.allottedSpots.put(spotType, new HashMap<>());
        }
    }


    @Override
    public synchronized Optional<SingleSpot> findAndAllotSpot(SingleSpot spot) {
        Preconditions.checkArgument(spot != null && spot.spotType() != null, " Spot Type cannot be null");
        if (!this.unAllottedSpots.containsKey(spot.spotType()) || this.unAllottedSpots.get(spot.spotType()).isEmpty()) {
            return Optional.ofNullable(null);
        }
        SingleSpot allottedspot = this.unAllottedSpots.get(spot.spotType()).pollFirst();
        if (allottedspot != null) {

            this.allottedSpots.get(allottedspot.spotType()).put(allottedspot.spotId(), allottedspot);
        }

        return Optional.ofNullable(allottedspot);
    }

    @Override
    public synchronized void unAllotSpot(Long spotId) {

        if (this.spotIdToSpotMap.containsKey(spotId)) {
            SingleSpot spot = this.spotIdToSpotMap.get(spotId);
            SingleSpot freedspot = this.allottedSpots.get(spot.spotType()).getOrDefault(spot.spotId(), null);
            if (freedspot != null) {
                this.allottedSpots.get(spot.spotType()).remove(spot.spotId());
                this.unAllottedSpots.get(freedspot.spotType()).add(freedspot);
            } else {
                throw new InvalidSpotIdException(spotId);
            }
        } else {
            throw new InvalidSpotIdException(spotId);
        }
    }

    @Override
    public SingleSpot getSpot(Long id) {
        if (this.spotIdToSpotMap.containsKey(id)) {
            return this.spotIdToSpotMap.get(id);
        } else {
            throw new InvalidSpotIdException(id);
        }

    }


}
