import java.util.function.Supplier;

// Supplier<T> is a built-in Java interface with one method: T get()
// It represents a "recipe" for creating a T — nothing is created until you call get().

class SupplierExplained {
    public static void main(String[] args) {

        // A Supplier stores HOW to make something, not the thing itself
        Supplier<Vehicle> carMaker = () -> new Car(4);

        Vehicle v1 = carMaker.get(); // creates a new Car
        Vehicle v2 = carMaker.get(); // creates another new Car — fresh each time

        // A Map<String, Supplier<Vehicle>> lets you look up a recipe by name
        java.util.Map<String, Supplier<Vehicle>> creators = new java.util.HashMap<>();
        creators.put("car",        () -> new Car(4));
        creators.put("truck",      () -> new Truck(5.0));
        creators.put("motorcycle", () -> new Motorcycle("sport"));

        // To build one: look up the recipe, then call get()
        Vehicle vehicle = creators.get("car").get();

        // This is exactly the pattern you need to implement in VehicleFactory
    }
}
