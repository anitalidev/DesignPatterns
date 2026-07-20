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

    static boolean approx(double a, double b) { return Math.abs(a - b) < 0.0001; }

    public static void main(String[] args) {
        test("ShapeFactory.create() exists", () -> {
            Shape s = ShapeFactory.create("circle", 1);
            assertTrue(s != null, "create() must return non-null");
        });

        test("create('circle', r) has correct area()", () -> {
            Shape s = ShapeFactory.create("circle", 5);
            assertTrue(approx(s.area(), Math.PI * 25), "Expected PI*25, got " + s.area());
        });

        test("create('rectangle', w, h) has correct area()", () -> {
            Shape s = ShapeFactory.create("rectangle", 4, 6);
            assertTrue(approx(s.area(), 24), "Expected 24, got " + s.area());
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

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
