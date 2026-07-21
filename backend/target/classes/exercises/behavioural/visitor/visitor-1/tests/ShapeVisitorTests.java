class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static boolean approx(double a, double b) { return Math.abs(a - b) < 0.0001; }

    public static void main(String[] args) {
        test("AreaVisitor: circle area = π*r²", () -> {
            double area = new Circle(5).accept(new AreaVisitor());
            assertTrue(approx(area, Math.PI * 25), "circle area should be π*25, got " + area);
        });
        test("AreaVisitor: rectangle area = w*h", () -> {
            double area = new Rectangle(4, 6).accept(new AreaVisitor());
            assertTrue(approx(area, 24), "rectangle area should be 24, got " + area);
        });
        test("PerimeterVisitor: circle perimeter = 2*π*r", () -> {
            double p = new Circle(3).accept(new PerimeterVisitor());
            assertTrue(approx(p, 2 * Math.PI * 3), "circle perimeter should be 2π*3, got " + p);
        });
        test("PerimeterVisitor: rectangle perimeter = 2*(w+h)", () -> {
            double p = new Rectangle(3, 4).accept(new PerimeterVisitor());
            assertTrue(approx(p, 14), "rectangle perimeter should be 14, got " + p);
        });
        test("Circle.accept() dispatches to visitCircle", () -> {
            final boolean[] called = {false};
            ShapeVisitor v = new ShapeVisitor() {
                public double visitCircle(Circle c)       { called[0] = true; return 0; }
                public double visitRectangle(Rectangle r) { return 0; }
            };
            new Circle(1).accept(v);
            assertTrue(called[0], "accept() on Circle should call visitCircle");
        });
        test("Rectangle.accept() dispatches to visitRectangle", () -> {
            final boolean[] called = {false};
            ShapeVisitor v = new ShapeVisitor() {
                public double visitCircle(Circle c)       { return 0; }
                public double visitRectangle(Rectangle r) { called[0] = true; return 0; }
            };
            new Rectangle(1, 1).accept(v);
            assertTrue(called[0], "accept() on Rectangle should call visitRectangle");
        });
        test("same visitor can handle multiple shapes in sequence", () -> {
            AreaVisitor av = new AreaVisitor();
            double a1 = new Circle(1).accept(av);
            double a2 = new Rectangle(2, 3).accept(av);
            assertTrue(approx(a1, Math.PI) && approx(a2, 6), "visitor should handle both shapes correctly");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
