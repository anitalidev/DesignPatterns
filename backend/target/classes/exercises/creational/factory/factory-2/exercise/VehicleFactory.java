import java.util.function.Supplier;

// TODO: Replace this switch-based factory with one that stores creators in a
// Map<String, Supplier<Vehicle>> so new vehicle types can be registered at
// runtime without modifying this class.
//
// Requirements:
//   - Rename the class to VehicleFactory
//   - Add a register(String type, Supplier<Vehicle> creator) method
//   - Add a create(String type) method that looks up and calls the supplier,
//     throwing IllegalArgumentException for unknown types
//   - Pre-register "car", "truck", and "motorcycle" with default constructors
//     (Car(4), Truck(5.0), Motorcycle("sport"))

class VehicleCreator {
    static Vehicle get(String type) {
        switch (type) {
            case "car":        return new Car(4);
            case "truck":      return new Truck(5.0);
            case "motorcycle": return new Motorcycle("sport");
            default: throw new IllegalArgumentException("Unknown vehicle: " + type);
        }
    }
}
