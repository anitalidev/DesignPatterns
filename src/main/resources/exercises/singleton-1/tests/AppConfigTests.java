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
        test("getInstance() returns an object", () -> {
            AppConfig cfg = AppConfig.getInstance();
            assertTrue(cfg != null, "getInstance() must return non-null");
        });

        test("getInstance() always returns the same instance", () -> {
            AppConfig a = AppConfig.getInstance();
            AppConfig b = AppConfig.getInstance();
            assertTrue(a == b, "Two calls must return the same instance");
        });

        test("set() and get() still work", () -> {
            AppConfig cfg = AppConfig.getInstance();
            cfg.set("theme", "dark");
            assertTrue("dark".equals(cfg.get("theme")), "get('theme') should return 'dark'");
        });

        test("State is shared across all getInstance() calls", () -> {
            AppConfig a = AppConfig.getInstance();
            a.set("language", "fr");
            AppConfig b = AppConfig.getInstance();
            assertTrue("fr".equals(b.get("language")), "Changes must be visible through another reference");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
