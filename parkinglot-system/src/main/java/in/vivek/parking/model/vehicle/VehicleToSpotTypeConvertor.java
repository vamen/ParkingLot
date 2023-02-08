package in.vivek.parking.model.vehicle;


public class VehicleToSpotTypeConvertor {

    private static VehicleToSpotTypeConvertor vehicleToSpotTypeConvertor = null;

    public static VehicleToSpotTypeConvertor getInstance() {
        if (vehicleToSpotTypeConvertor == null) {
            synchronized (VehicleToSpotTypeConvertor.class) {
                if (vehicleToSpotTypeConvertor == null) {
                    vehicleToSpotTypeConvertor = new VehicleToSpotTypeConvertor();
                }
            }
        }
        return vehicleToSpotTypeConvertor;
    }

    public SpotType getSpotType(Vehicle vehicle) {
        switch (vehicle.getVehicleType()) {
            case TWO_WHEEL:
                return SpotType.TWO_WHEEL;
            case LARGE_FOUR_WHEEL:
                return SpotType.LARGE_FOUR_WHEEL;
            case SMALL_FOUR_WHEEL:
                return SpotType.SMALL_FOUR_WHEEL;
            default:
                throw new RuntimeException(String.format("SPOT TYPE NOT SUPPORTED FOR ENUM %s", vehicle.getVehicleType()));
        }
    }
}
