class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("getInstance() returns a non-null Logger", () ->
            assertTrue(Logger.getInstance("database") != null, "getInstance() must not return null"));
        test("getInstance() with the same name returns the same instance", () -> {
            Logger a = Logger.getInstance("network"); Logger b = Logger.getInstance("network");
            assertTrue(a == b, "same name should return the identical Logger instance");
        });
        test("getInstance() with different names returns different instances", () ->
            assertTrue(Logger.getInstance("db") != Logger.getInstance("net"), "different names → different instances"));
        test("getModule() returns the correct module name", () ->
            assertEquals("auth", Logger.getInstance("auth").getModule(), "getModule() should return the name passed in"));
        test("log() adds a message to this logger's list", () -> {
            Logger logger = Logger.getInstance("cache"); logger.log("cache miss");
            assertTrue(logger.getMessages().contains("cache miss"), "message should appear in getMessages()");
        });
        test("messages logged are visible from another getInstance() call with the same name", () -> {
            Logger.getInstance("storage").log("write started");
            assertTrue(Logger.getInstance("storage").getMessages().contains("write started"),
                "shared instance should see previously logged messages");
        });
        test("two loggers have independent message lists", () -> {
            Logger ui = Logger.getInstance("ui"); Logger api = Logger.getInstance("api");
            ui.log("button clicked"); api.log("request sent");
            assertTrue(!ui.getMessages().contains("request sent"),    "ui logger should not see api messages");
            assertTrue(!api.getMessages().contains("button clicked"), "api logger should not see ui messages");
        });
        test("multiple messages accumulate in the same logger", () -> {
            Logger logger = Logger.getInstance("batch");
            logger.log("step 1"); logger.log("step 2"); logger.log("step 3");
            assertEquals(3, logger.getMessages().size(), "logger should accumulate all messages");
        });
        test("10 calls with the same name all return the same instance", () -> {
            Logger first = Logger.getInstance("stress");
            for (int i = 0; i < 9; i++)
                assertTrue(Logger.getInstance("stress") == first, "call " + i + " should return same instance");
        });

        test("constructor is private", () -> {
            try {
                java.lang.reflect.Constructor<Logger> c = Logger.class.getDeclaredConstructor(String.class);
                assertTrue(java.lang.reflect.Modifier.isPrivate(c.getModifiers()), "Logger constructor must be private");
            } catch (NoSuchMethodException e) { throw new AssertionError("Could not find Logger(String) constructor"); }
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
