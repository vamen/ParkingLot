package in.vivek.parking.feestrategy.model;

import in.vivek.parking.model.vehicle.SpotType;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;

@Getter
@Data
public class FeeModel<T extends FeeStructure> extends HashMap<SpotType, T> {

}
