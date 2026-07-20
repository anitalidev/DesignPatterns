import java.util.function.Consumer;

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
        test("Store has subscribe() and unsubscribe()", () -> {
            Store s = new Store();
            Consumer<String> fn = st -> {};
            s.subscribe(fn);
            s.unsubscribe(fn);
        });

        test("Subscriber is called on setState()", () -> {
            Store s = new Store();
            boolean[] called = {false};
            s.subscribe(st -> called[0] = true);
            s.setState("x");
            assertTrue(called[0], "Subscriber was not called after setState()");
        });

        test("Subscriber receives the new state", () -> {
            Store s = new Store();
            String[] received = {null};
            s.subscribe(st -> received[0] = st);
            s.setState("hello");
            assertTrue("hello".equals(received[0]), "Subscriber should receive 'hello'");
        });

        test("Multiple subscribers are all notified", () -> {
            Store s = new Store();
            int[] a = {0}, b = {0};
            s.subscribe(st -> a[0]++);
            s.subscribe(st -> b[0]++);
            s.setState("x");
            assertTrue(a[0] == 1 && b[0] == 1, "Both subscribers must be called");
        });

        test("unsubscribe() stops the callback", () -> {
            Store s = new Store();
            int[] count = {0};
            Consumer<String> fn = st -> count[0]++;
            s.subscribe(fn);
            s.setState("a");
            s.unsubscribe(fn);
            s.setState("b");
            assertTrue(count[0] == 1, "Unsubscribed callback must not fire again");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
