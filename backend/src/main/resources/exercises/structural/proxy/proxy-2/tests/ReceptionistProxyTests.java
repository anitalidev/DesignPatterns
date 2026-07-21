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
        test("ReceptionistProxy implements Person", () -> {
            Person proxy = new ReceptionistProxy(new Executive());
            assertTrue(proxy != null, "proxy should be assignable to Person");
        });

        test("visitor with appointment is passed through to Executive", () -> {
            Executive exec = new Executive();
            ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice");
            proxy.meet("Alice");
            assertTrue(exec.getMeetings().contains("Alice"), "Alice should reach the Executive");
        });

        test("meet() returns Executive's response for approved visitor", () -> {
            ReceptionistProxy proxy = new ReceptionistProxy(new Executive());
            proxy.addAppointment("Alice");
            String result = proxy.meet("Alice");
            assertEquals("Executive is meeting Alice", result, "should return Executive's meeting message");
        });

        test("visitor without appointment is turned away", () -> {
            Executive exec = new Executive();
            ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.meet("Bob");
            assertTrue(exec.getMeetings().isEmpty(), "Bob should not reach the Executive");
        });

        test("refusal message includes the visitor's name", () -> {
            ReceptionistProxy proxy = new ReceptionistProxy(new Executive());
            String result = proxy.meet("Bob");
            assertTrue(result.contains("Bob"), "refusal message should mention Bob's name");
        });

        test("multiple appointments can be added", () -> {
            Executive exec = new Executive();
            ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice");
            proxy.addAppointment("Carol");
            proxy.meet("Alice");
            proxy.meet("Carol");
            assertEquals(2, exec.getMeetings().size(), "both approved visitors should reach the Executive");
        });

        test("approved visitor passes while unapproved is blocked in the same session", () -> {
            Executive exec = new Executive();
            ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice");
            proxy.meet("Alice");
            proxy.meet("Bob");
            assertEquals(1, exec.getMeetings().size(), "only Alice should reach the Executive");
            assertTrue(exec.getMeetings().contains("Alice"), "Alice should be in meetings");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
