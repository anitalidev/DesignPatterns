class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("Order implements Auditable", () ->
            assertTrue(new Order("ORD-1") instanceof Auditable, "Order should implement Auditable"));
        test("Payment implements Auditable", () ->
            assertTrue(new Payment(99.99) instanceof Auditable, "Payment should implement Auditable"));
        test("Product does NOT implement Auditable", () ->
            assertTrue(!(new Product("Widget") instanceof Auditable), "Product should not implement Auditable"));
        test("log() records an Order entry", () -> {
            AuditLogger logger = new AuditLogger(); logger.log(new Order("ORD-1"));
            assertEquals(1, logger.getEntries().size(), "one entry should be recorded for Order");
        });
        test("log() records a Payment entry", () -> {
            AuditLogger logger = new AuditLogger(); logger.log(new Payment(50.0));
            assertEquals(1, logger.getEntries().size(), "one entry should be recorded for Payment");
        });
        test("log() ignores a Product", () -> {
            AuditLogger logger = new AuditLogger(); logger.log(new Product("Widget"));
            assertEquals(0, logger.getEntries().size(), "Product should not produce an audit entry");
        });
        test("log() handles a mix of auditable and non-auditable objects", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Order("ORD-1")); logger.log(new Product("Widget")); logger.log(new Payment(25.0));
            assertEquals(2, logger.getEntries().size(), "only Order and Payment should be recorded");
        });
        test("entries list is empty when nothing auditable is logged", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Product("A")); logger.log(new Product("B"));
            assertEquals(0, logger.getEntries().size(), "no entries for non-auditable objects");
        });
        test("multiple auditable objects all get recorded", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Order("ORD-1")); logger.log(new Order("ORD-2")); logger.log(new Payment(10.0));
            assertEquals(3, logger.getEntries().size(), "three auditable objects should produce three entries");
        });
        test("each log() call adds one entry per auditable object", () -> {
            AuditLogger logger = new AuditLogger();
            for (int i = 0; i < 5; i++) logger.log(new Order("ORD-" + i));
            assertEquals(5, logger.getEntries().size(), "five orders should produce five entries");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
