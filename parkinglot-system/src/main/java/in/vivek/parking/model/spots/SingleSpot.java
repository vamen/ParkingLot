package in.vivek.parking.model.spots;

import in.vivek.parking.model.vehicle.SpotType;
import lombok.Builder;

@Builder
public class SingleSpot implements Spot {

    Long spotId;
    SpotType spotType;


    @Override
    public Long spotId() {
        return this.spotId;
    }

    @Override
    public SpotType spotType() {
        return this.spotType;
    }
}
