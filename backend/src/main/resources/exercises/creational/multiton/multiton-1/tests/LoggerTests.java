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
        test("getInstance() returns a non-null Logger", () -> {
            Logger logger = Logger.getInstance("database");
            assertTrue(logger != null, "getInstance() must not return null");
        });

        test("getInstance() with the same name returns the same instance", () -> {
            Logger a = Logger.getInstance("network");
            Logger b = Logger.getInstance("network");
            assertTrue(a == b, "same name should return the identical Logger instance");
        });

        test("getInstance() with different names returns different instances", () -> {
            Logger db  = Logger.getInstance("db");
            Logger net = Logger.getInstance("net");
            assertTrue(db != net, "different names should return different Logger instances");
        });

        test("getModule() returns the correct module name", () -> {
            Logger logger = Logger.getInstance("auth");
            assertEquals("auth", logger.getModule(), "getModule() should return the name passed to getInstance()");
        });

        test("log() adds a message to this logger's list", () -> {
            Logger logger = Logger.getInstance("cache");
            logger.log("cache miss");
            assertTrue(logger.getMessages().contains("cache miss"), "message should appear in getMessages()");
        });

        test("messages logged by one instance are visible from another getInstance() call with the same name", () -> {
            Logger.getInstance("storage").log("write started");
            Logger same = Logger.getInstance("storage");
            assertTrue(same.getMessages().contains("write started"), "shared instance should see previously logged messages");
        });

        test("two loggers have independent message lists", () -> {
            Logger ui  = Logger.getInstance("ui");
            Logger api = Logger.getInstance("api");
            ui.log("button clicked");
            api.log("request sent");
            assertTrue(!ui.getMessages().contains("request sent"), "ui logger should not see api messages");
            assertTrue(!api.getMessages().contains("button clicked"), "api logger should not see ui messages");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
