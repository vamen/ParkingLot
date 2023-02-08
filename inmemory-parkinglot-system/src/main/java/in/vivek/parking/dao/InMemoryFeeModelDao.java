package in.vivek.parking.dao;

import in.vivek.parking.feestrategy.model.FeeModel;
import in.vivek.parking.feestrategy.model.FeeStructure;
import in.vivek.parking.model.vehicle.SpotType;

public class InMemoryFeeModelDao<T extends FeeStructure> implements FeeModelDao<T> {

    private final FeeModel<T> feeModel;

    public InMemoryFeeModelDao(FeeModel<T> feeModel) {
        // TODO: make fee model read only
        this.feeModel = feeModel;
    }


    @Override
    public T getFeeStructure(SpotType spotType) {
        return this.feeModel.get(spotType);
    }

    @Override
    public boolean containsSpotType(SpotType spotType) {
        return this.feeModel.containsKey(spotType);

    }
}
