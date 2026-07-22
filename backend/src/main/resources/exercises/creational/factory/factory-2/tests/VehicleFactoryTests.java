class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }

    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();

        test("create('car') returns non-null", () ->
            assertTrue(factory.create("car") != null, "Expected non-null Vehicle"));
        test("create('car') describe() is correct", () ->
            assertEquals("Car with 4 seats", factory.create("car").describe(), "Got: " + factory.create("car").describe()));
        test("create('truck') describe() is correct", () ->
            assertEquals("Truck with 5.0t payload", factory.create("truck").describe(), "Got: " + factory.create("truck").describe()));
        test("create('motorcycle') describe() is correct", () ->
            assertEquals("sport motorcycle", factory.create("motorcycle").describe(), "Got: " + factory.create("motorcycle").describe()));
        test("create() throws for unknown type", () -> {
            boolean threw = false;
            try { factory.create("spaceship"); } catch (IllegalArgumentException e) { threw = true; }
            assertTrue(threw, "Expected IllegalArgumentException for unknown type");
        });
        test("register() adds new vehicle type", () -> {
            factory.register("bus", () -> new Car(50));
            assertTrue(factory.create("bus") != null, "Registered type 'bus' should be creatable");
        });
        test("registered type produces correct describe()", () -> {
            factory.register("minicar", () -> new Car(2));
            assertEquals("Car with 2 seats", factory.create("minicar").describe(), "registered minicar should have 2 seats");
        });
        test("create() still throws for a different unknown type after register()", () -> {
            factory.register("van", () -> new Car(8));
            boolean threw = false;
            try { factory.create("submarine"); } catch (IllegalArgumentException e) { threw = true; }
            assertTrue(threw, "should still throw for unregistered types");
        });
        test("each create() call returns a new instance", () -> {
            Vehicle a = factory.create("car"); Vehicle b = factory.create("car");
            assertTrue(a != b, "each create() call should return a new Vehicle instance");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
