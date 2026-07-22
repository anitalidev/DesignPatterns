import java.util.function.Supplier;

class VehicleFactory {
    // TODO: Add a Map<String, Supplier<Vehicle>> to store registered creators

    // TODO: Pre-register "car", "truck", and "motorcycle" in a constructor or
    //       static initialiser using Car(4), Truck(5.0), Motorcycle("sport")

    public void register(String type, Supplier<Vehicle> creator) {
        // TODO: Store the creator in the map
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Vehicle create(String type) {
        // TODO: Look up the type and call its supplier;
        //       throw IllegalArgumentException for unknown types
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
