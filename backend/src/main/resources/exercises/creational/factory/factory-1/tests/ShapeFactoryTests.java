class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static boolean approx(double a, double b) { return Math.abs(a - b) < 0.0001; }

    public static void main(String[] args) {
        test("create('circle', r) returns non-null", () -> {
            assertTrue(ShapeFactory.create("circle", 1) != null, "expected non-null, got: null");
        });
        test("create('circle', r) returns a Shape", () -> {
            Shape s = ShapeFactory.create("circle", 1);
            assertTrue(s instanceof Shape, "circle should be a Shape — got: " + s.getClass().getName());
        });
        test("create('circle', r) has correct area()", () -> {
            Shape s = ShapeFactory.create("circle", 5);
            assertTrue(approx(s.area(), Math.PI * 25), "Expected PI*25, got " + s.area());
        });
        test("create('rectangle', w, h) returns a Shape", () -> {
            Shape s = ShapeFactory.create("rectangle", 4, 6);
            assertTrue(s instanceof Shape, "rectangle should be a Shape — got: " + s.getClass().getName());
        });
        test("create('rectangle', w, h) has correct area()", () -> {
            Shape s = ShapeFactory.create("rectangle", 4, 6);
            assertTrue(approx(s.area(), 24), "Expected 24, got " + s.area());
        });
        test("create('triangle', b, h) returns a Shape", () -> {
            Shape s = ShapeFactory.create("triangle", 3, 8);
            assertTrue(s instanceof Shape, "triangle should be a Shape — got: " + s.getClass().getName());
        });
        test("create('triangle', b, h) has correct area()", () -> {
            Shape s = ShapeFactory.create("triangle", 3, 8);
            assertTrue(approx(s.area(), 12), "Expected 12, got " + s.area());
        });
        test("create() throws for unknown types", () -> {
            boolean threw = false;
            try { ShapeFactory.create("hexagon"); } catch (Exception e) { threw = true; }
            assertTrue(threw, "create() should throw for an unrecognised type");
        });
        test("create() called twice returns independent instances", () -> {
            Shape a = ShapeFactory.create("circle", 5); Shape b = ShapeFactory.create("circle", 5);
            assertTrue(a != b, "each create() call should return a new instance");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
