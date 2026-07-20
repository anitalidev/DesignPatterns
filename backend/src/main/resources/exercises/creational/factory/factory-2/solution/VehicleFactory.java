import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

interface Vehicle {
    String describe();
}

class Car implements Vehicle {
    private int seats;
    Car(int seats) { this.seats = seats; }
    public String describe() { return "Car with " + seats + " seats"; }
}

class Truck implements Vehicle {
    private double payload;
    Truck(double payload) { this.payload = payload; }
    public String describe() { return "Truck with " + payload + "t payload"; }
}

class Motorcycle implements Vehicle {
    private String style;
    Motorcycle(String style) { this.style = style; }
    public String describe() { return style + " motorcycle"; }
}

class VehicleFactory {
    private final Map<String, Supplier<Vehicle>> creators = new HashMap<>();

    VehicleFactory() {
        register("car",        () -> new Car(4));
        register("truck",      () -> new Truck(5.0));
        register("motorcycle", () -> new Motorcycle("sport"));
    }

    void register(String type, Supplier<Vehicle> creator) {
        creators.put(type, creator);
    }

    Vehicle create(String type) {
        Supplier<Vehicle> creator = creators.get(type);
        if (creator == null) throw new IllegalArgumentException("Unknown vehicle: " + type);
        return creator.get();
    }
}
