class TestRunner {
    static int passed = 0, failed = 0;

    static void test(String name, Runnable fn) {
        try {
            fn.run();
            System.out.println("PASS: " + name);
            passed++;
        } catch (Throwable e) {
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
        // Marker assignments
        test("UserSession implements Auditable", () ->
            assertTrue(new UserSession("u1") instanceof Auditable, "UserSession should be Auditable"));

        test("UserSession implements Cacheable", () ->
            assertTrue(new UserSession("u1") instanceof Cacheable, "UserSession should be Cacheable"));

        test("AuditEvent implements Auditable", () ->
            assertTrue(new AuditEvent("login") instanceof Auditable, "AuditEvent should be Auditable"));

        test("AuditEvent does NOT implement Cacheable", () ->
            assertTrue(!(new AuditEvent("login") instanceof Cacheable), "AuditEvent should not be Cacheable"));

        test("StaticAsset implements Cacheable", () ->
            assertTrue(new StaticAsset("/logo.png") instanceof Cacheable, "StaticAsset should be Cacheable"));

        test("StaticAsset does NOT implement Auditable", () ->
            assertTrue(!(new StaticAsset("/logo.png") instanceof Auditable), "StaticAsset should not be Auditable"));

        test("TempData implements neither marker", () -> {
            Object t = new TempData("scratch");
            assertTrue(!(t instanceof Auditable), "TempData should not be Auditable");
            assertTrue(!(t instanceof Cacheable), "TempData should not be Cacheable");
        });

        // AuditLogger
        test("AuditLogger records Auditable objects", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new AuditEvent("login"));
            logger.log(new UserSession("u1"));
            assertEquals(2, logger.getEntries().size(), "both Auditable objects should be logged");
        });

        test("AuditLogger ignores non-Auditable objects", () -> {
            AuditLogger logger = new AuditLogger();
            logger.log(new StaticAsset("/img.png"));
            logger.log(new TempData("scratch"));
            assertEquals(0, logger.getEntries().size(), "non-Auditable objects should not be logged");
        });

        // ResponseCache
        test("ResponseCache stores Cacheable objects", () -> {
            ResponseCache cache = new ResponseCache();
            StaticAsset asset = new StaticAsset("/logo.png");
            cache.store("logo", asset);
            assertTrue(cache.retrieve("logo") == asset, "stored Cacheable should be retrievable");
        });

        test("ResponseCache stores UserSession (both markers)", () -> {
            ResponseCache cache = new ResponseCache();
            UserSession session = new UserSession("u1");
            cache.store("session-u1", session);
            assertTrue(cache.retrieve("session-u1") == session, "UserSession should be cacheable");
        });

        test("ResponseCache rejects non-Cacheable objects", () -> {
            ResponseCache cache = new ResponseCache();
            cache.store("event", new AuditEvent("login"));
            assertTrue(cache.retrieve("event") == null, "non-Cacheable object should not be stored");
        });

        test("ResponseCache returns null for unknown keys", () -> {
            ResponseCache cache = new ResponseCache();
            assertTrue(cache.retrieve("missing") == null, "missing key should return null");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
