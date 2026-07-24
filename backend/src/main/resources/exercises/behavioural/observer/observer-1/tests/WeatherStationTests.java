class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }

    public static void main(String[] args) {
        // WeatherStation tests
        test("observer is notified when temperature is set", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d = new TemperatureDisplay();
            ws.addObserver(d);
            ws.setTemperature(22.0);
            assertEquals(22.0, d.getLatest(), "display should receive 22.0");
        });
        test("all registered observers are notified", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d1 = new TemperatureDisplay();
            TemperatureDisplay d2 = new TemperatureDisplay();
            ws.addObserver(d1); ws.addObserver(d2);
            ws.setTemperature(15.0);
            assertEquals(15.0, d1.getLatest(), "d1 should receive 15.0");
            assertEquals(15.0, d2.getLatest(), "d2 should receive 15.0");
        });
        test("removed observer is not notified", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d = new TemperatureDisplay();
            ws.addObserver(d);
            ws.setTemperature(10.0);
            ws.removeObserver(d);
            ws.setTemperature(99.0);
            assertEquals(10.0, d.getLatest(), "removed observer should still show 10.0, not 99.0");
        });
        test("removing one observer leaves others active", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d1 = new TemperatureDisplay();
            TemperatureDisplay d2 = new TemperatureDisplay();
            ws.addObserver(d1); ws.addObserver(d2);
            ws.setTemperature(5.0);
            ws.removeObserver(d1);
            ws.setTemperature(30.0);
            assertEquals(5.0, d1.getLatest(), "removed d1 should still show 5.0");
            assertEquals(30.0, d2.getLatest(), "d2 should show latest 30.0");
        });
        test("observer receives each temperature update", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d = new TemperatureDisplay();
            ws.addObserver(d);
            ws.setTemperature(1.0); ws.setTemperature(2.0); ws.setTemperature(3.0);
            assertEquals(3.0, d.getLatest(), "display should show the most recent temperature");
        });

        // TemperatureDisplay tests
        test("getReadingCount() starts at 0", () -> {
            assertEquals(0, new TemperatureDisplay().getReadingCount(), "new display should have 0 readings");
        });
        test("getReadingCount() increments on each update", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d = new TemperatureDisplay();
            ws.addObserver(d);
            ws.setTemperature(10.0); ws.setTemperature(20.0); ws.setTemperature(30.0);
            assertEquals(3, d.getReadingCount(), "should have 3 readings");
        });
        test("getLatest() returns the most recent temperature", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d = new TemperatureDisplay();
            ws.addObserver(d);
            ws.setTemperature(18.0); assertEquals(18.0, d.getLatest(), "after 18.0");
            ws.setTemperature(25.0); assertEquals(25.0, d.getLatest(), "after 25.0");
        });
        test("two displays are independent", () -> {
            WeatherStation ws = new WeatherStation();
            TemperatureDisplay d1 = new TemperatureDisplay();
            TemperatureDisplay d2 = new TemperatureDisplay();
            ws.addObserver(d1); ws.addObserver(d2);
            ws.setTemperature(10.0);
            ws.removeObserver(d2);
            ws.setTemperature(20.0);
            assertEquals(2, d1.getReadingCount(), "d1 received both updates");
            assertEquals(1, d2.getReadingCount(), "d2 only received one update");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
