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
