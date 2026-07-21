import java.util.Map;

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
        test("AnalyticsAdapter implements Tracker", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            assertTrue(tracker != null, "AnalyticsAdapter must be assignable to Tracker");
        });

        test("track() delegates to Analytics.recordEvent()", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("button_click", Map.of("id", "submit"));
            assertEquals(1, sdk.getRecorded().size(), "recordEvent() should have been called once");
        });

        test("track() passes the event name correctly", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("page_view", Map.of("page", "/home"));
            assertTrue(sdk.getRecorded().get(0).startsWith("page_view:"), "recorded entry should start with the event name");
        });

        test("track() passes the data map correctly", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("purchase", Map.of("item", "book", "price", "9.99"));
            String entry = sdk.getRecorded().get(0);
            assertTrue(entry.contains("book"),  "recorded entry should contain data value 'book'");
            assertTrue(entry.contains("price"), "recorded entry should contain data key 'price'");
        });

        test("multiple track() calls each reach Analytics", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("login",  Map.of("user", "alice"));
            tracker.track("logout", Map.of("user", "alice"));
            assertEquals(2, sdk.getRecorded().size(), "both events should be recorded");
        });

        test("adapter works when used as a Tracker reference", () -> {
            Analytics sdk = new Analytics();
            Tracker tracker = new AnalyticsAdapter(sdk);
            tracker.track("signup", Map.of("plan", "pro"));
            assertTrue(!sdk.getRecorded().isEmpty(), "event should reach the SDK through the Tracker interface");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
