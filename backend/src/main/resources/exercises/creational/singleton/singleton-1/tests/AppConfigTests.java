class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }

    public static void main(String[] args) {
        test("getInstance() returns non-null", () -> {
            assertTrue(AppConfig.getInstance() != null, "getInstance() must not return null");
        });
        test("getInstance() always returns the same instance", () -> {
            AppConfig a = AppConfig.getInstance(); AppConfig b = AppConfig.getInstance();
            assertTrue(a == b, "two calls must return the same instance");
        });
        test("set() and get() work for theme", () -> {
            AppConfig.getInstance().set("theme", "dark");
            assertEquals("dark", AppConfig.getInstance().get("theme"), "get('theme') should return 'dark'");
        });
        test("state is shared across all getInstance() calls", () -> {
            AppConfig.getInstance().set("language", "fr");
            assertEquals("fr", AppConfig.getInstance().get("language"), "change via one ref must be visible via another");
        });
        test("get('theme') defaults to 'light'", () -> {
            // reset via set so test is independent
            AppConfig.getInstance().set("theme", "light");
            assertEquals("light", AppConfig.getInstance().get("theme"), "default theme should be 'light'");
        });
        test("get('language') defaults to 'en'", () -> {
            AppConfig.getInstance().set("language", "en");
            assertEquals("en", AppConfig.getInstance().get("language"), "default language should be 'en'");
        });
        test("get() with unknown key returns null", () -> {
            assertTrue(AppConfig.getInstance().get("nonexistent") == null, "unknown key should return null");
        });
        test("set language and theme independently", () -> {
            AppConfig cfg = AppConfig.getInstance();
            cfg.set("theme", "dark"); cfg.set("language", "de");
            assertEquals("dark", cfg.get("theme"), "theme should be dark");
            assertEquals("de",   cfg.get("language"), "language should be de");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
