import java.util.Map;

class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("AnalyticsAdapter implements Tracker", () -> {
            Tracker tracker = new AnalyticsAdapter(new Analytics());
            assertTrue(tracker != null, "AnalyticsAdapter must be assignable to Tracker");
        });
        test("track() delegates to Analytics.recordEvent()", () -> {
            Analytics sdk = new Analytics(); new AnalyticsAdapter(sdk).track("button_click", Map.of("id", "submit"));
            assertEquals(1, sdk.getRecorded().size(), "recordEvent() should have been called once");
        });
        test("track() passes the event name correctly", () -> {
            Analytics sdk = new Analytics(); new AnalyticsAdapter(sdk).track("page_view", Map.of("page", "/home"));
            assertTrue(sdk.getRecorded().get(0).startsWith("page_view:"), "recorded entry should start with event name");
        });
        test("track() passes the data map correctly", () -> {
            Analytics sdk = new Analytics();
            new AnalyticsAdapter(sdk).track("purchase", Map.of("item", "book", "price", "9.99"));
            String entry = sdk.getRecorded().get(0);
            assertTrue(entry.contains("book"),  "recorded entry should contain 'book'");
            assertTrue(entry.contains("price"), "recorded entry should contain 'price'");
        });
        test("multiple track() calls each reach Analytics", () -> {
            Analytics sdk = new Analytics(); Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("login", Map.of("user", "alice")); tracker.track("logout", Map.of("user", "alice"));
            assertEquals(2, sdk.getRecorded().size(), "both events should be recorded");
        });
        test("adapter works when used as a Tracker reference", () -> {
            Analytics sdk = new Analytics(); Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("signup", Map.of("plan", "pro"));
            assertTrue(!sdk.getRecorded().isEmpty(), "event should reach the SDK through the Tracker interface");
        });
        test("track() with empty data map still records the event", () -> {
            Analytics sdk = new Analytics(); new AnalyticsAdapter(sdk).track("heartbeat", Map.of());
            assertEquals(1, sdk.getRecorded().size(), "event with empty data should still be recorded");
        });
        test("two adapters sharing the same Analytics see each other's events", () -> {
            Analytics sdk = new Analytics();
            Tracker a = new AnalyticsAdapter(sdk); Tracker b = new AnalyticsAdapter(sdk);
            a.track("ev1", Map.of()); b.track("ev2", Map.of());
            assertEquals(2, sdk.getRecorded().size(), "both adapters' events should appear in the shared SDK");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
