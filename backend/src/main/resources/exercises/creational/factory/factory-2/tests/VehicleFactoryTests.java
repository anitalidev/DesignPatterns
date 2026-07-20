class TestRunner {
    static int passed = 0, failed = 0;

    static void test(String name, Runnable fn) {
        try {
            fn.run();
            System.out.println("PASS: " + name);
            passed++;
        } catch (Exception | AssertionError e) {
            System.out.println("FAIL: " + name + " | " + e.getMessage());
            failed++;
        }
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();

        test("create('car') returns non-null", () -> {
            assertTrue(factory.create("car") != null, "Expected non-null Vehicle");
        });

        test("create('car') describe() is correct", () -> {
            assertTrue(factory.create("car").describe().equals("Car with 4 seats"),
                "Got: " + factory.create("car").describe());
        });

        test("create('truck') describe() is correct", () -> {
            assertTrue(factory.create("truck").describe().equals("Truck with 5.0t payload"),
                "Got: " + factory.create("truck").describe());
        });

        test("create('motorcycle') describe() is correct", () -> {
            assertTrue(factory.create("motorcycle").describe().equals("sport motorcycle"),
                "Got: " + factory.create("motorcycle").describe());
        });

        test("create() throws for unknown type", () -> {
            boolean threw = false;
            try { factory.create("spaceship"); } catch (IllegalArgumentException e) { threw = true; }
            assertTrue(threw, "Expected IllegalArgumentException for unknown type");
        });

        test("register() adds new vehicle type", () -> {
            factory.register("bus", () -> new Car(50));
            Vehicle bus = factory.create("bus");
            assertTrue(bus != null, "Registered type 'bus' should be creatable");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
