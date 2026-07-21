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

    static void assertEquals(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) throw new AssertionError(msg + " — expected: " + expected + ", got: " + actual);
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        test("Order implements Auditable", () -> {
            Object order = new Order("ORD-1");
            assertTrue(order instanceof Auditable, "Order should implement Auditable");
        });

        test("Payment implements Auditable", () -> {
            Object payment = new Payment(99.99);
            assertTrue(payment instanceof Auditable, "Payment should implement Auditable");
        });

        test("Product does NOT implement Auditable", () -> {
            Object product = new Product("Widget");
            assertTrue(!(product instanceof Auditable), "Product should not implement Auditable");
        });

        test("log() records an Order entry", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Order("ORD-1"));
            assertEquals(1, logger.getEntries().size(), "one entry should be recorded for Order");
        });

        test("log() records a Payment entry", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Payment(50.0));
            assertEquals(1, logger.getEntries().size(), "one entry should be recorded for Payment");
        });

        test("log() ignores a Product", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Product("Widget"));
            assertEquals(0, logger.getEntries().size(), "Product should not produce an audit entry");
        });

        test("log() handles a mix of auditable and non-auditable objects", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Order("ORD-1"));
            logger.log(new Product("Widget"));
            logger.log(new Payment(25.0));
            assertEquals(2, logger.getEntries().size(), "only Order and Payment should be recorded");
        });

        test("entries list is empty when nothing auditable is logged", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new Product("A"));
            logger.log(new Product("B"));
            assertEquals(0, logger.getEntries().size(), "no entries for non-auditable objects");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
