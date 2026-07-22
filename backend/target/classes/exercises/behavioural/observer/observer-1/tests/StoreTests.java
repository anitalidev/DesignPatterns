import java.util.function.Consumer;

class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }

    public static void main(String[] args) {
        test("Store has subscribe() and unsubscribe()", () -> {
            Store s = new Store(); Consumer<String> fn = st -> {};
            s.subscribe(fn); s.unsubscribe(fn);
        });
        test("subscriber is called on setState()", () -> {
            Store s = new Store(); boolean[] called = {false};
            s.subscribe(st -> called[0] = true); s.setState("x");
            assertTrue(called[0], "subscriber was not called after setState()");
        });
        test("subscriber receives the new state value", () -> {
            Store s = new Store(); String[] got = {null};
            s.subscribe(st -> got[0] = st); s.setState("hello");
            assertEquals("hello", got[0], "subscriber should receive 'hello'");
        });
        test("all subscribers are notified", () -> {
            Store s = new Store(); int[] a = {0}, b = {0};
            s.subscribe(st -> a[0]++); s.subscribe(st -> b[0]++); s.setState("x");
            assertTrue(a[0] == 1 && b[0] == 1, "both subscribers must be called");
        });
        test("unsubscribe() stops the callback", () -> {
            Store s = new Store(); int[] count = {0};
            Consumer<String> fn = st -> count[0]++;
            s.subscribe(fn); s.setState("a"); s.unsubscribe(fn); s.setState("b");
            assertEquals(1, count[0], "unsubscribed callback must not fire again");
        });
        test("subscriber is called once per setState() call", () -> {
            Store s = new Store(); int[] count = {0};
            s.subscribe(st -> count[0]++);
            s.setState("a"); s.setState("b"); s.setState("c");
            assertEquals(3, count[0], "subscriber should be called once for each setState()");
        });
        test("subscriber receives correct value on each call", () -> {
            Store s = new Store(); String[] last = {null};
            s.subscribe(st -> last[0] = st);
            s.setState("first"); assertEquals("first", last[0], "after first setState");
            s.setState("second"); assertEquals("second", last[0], "after second setState");
        });
        test("unsubscribing one of two subscribers leaves the other active", () -> {
            Store s = new Store(); int[] a = {0}, b = {0};
            Consumer<String> fa = st -> a[0]++; Consumer<String> fb = st -> b[0]++;
            s.subscribe(fa); s.subscribe(fb);
            s.setState("x"); s.unsubscribe(fa); s.setState("y");
            assertEquals(1, a[0], "unsubscribed subscriber should have fired only once");
            assertEquals(2, b[0], "remaining subscriber should have fired twice");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
