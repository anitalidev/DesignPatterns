class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("ReceptionistProxy implements Person", () ->
            assertTrue(new ReceptionistProxy(new Executive()) != null, "proxy should be assignable to Person"));
        test("visitor with appointment is passed through", () -> {
            Executive exec = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice"); proxy.meet("Alice");
            assertTrue(exec.getMeetings().contains("Alice"), "Alice should reach the Executive — meetings: " + exec.getMeetings());
        });
        test("meet() returns Executive's response for approved visitor", () -> {
            ReceptionistProxy proxy = new ReceptionistProxy(new Executive());
            proxy.addAppointment("Alice");
            assertEquals("Executive is meeting Alice", proxy.meet("Alice"), "should return Executive's meeting message");
        });
        test("visitor without appointment is turned away", () -> {
            Executive exec = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.meet("Bob"); assertTrue(exec.getMeetings().isEmpty(), "Bob should not reach the Executive — meetings: " + exec.getMeetings());
        });
        test("refusal message includes the visitor's name", () -> {
            ReceptionistProxy proxy = new ReceptionistProxy(new Executive());
            String resp = proxy.meet("Bob");
            assertTrue(resp.contains("Bob"), "refusal message should mention Bob's name — got: \"" + resp + "\"");
        });
        test("multiple appointments can be added", () -> {
            Executive exec = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice"); proxy.addAppointment("Carol");
            proxy.meet("Alice"); proxy.meet("Carol");
            assertEquals(2, exec.getMeetings().size(), "both approved visitors should reach the Executive");
        });
        test("approved visitor passes while unapproved is blocked in same session", () -> {
            Executive exec = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice"); proxy.meet("Alice"); proxy.meet("Bob");
            assertEquals(1, exec.getMeetings().size(), "only Alice should reach the Executive");
        });
        test("appointment check is case-sensitive", () -> {
            Executive exec = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(exec);
            proxy.addAppointment("Alice"); proxy.meet("alice");
            assertTrue(exec.getMeetings().isEmpty(), "'alice' (lowercase) should not match appointment for 'Alice' — meetings: " + exec.getMeetings());
        });
        test("Executive is not contacted when visitor is rejected", () -> {
            Executive e = new Executive(); ReceptionistProxy proxy = new ReceptionistProxy(e);
            proxy.meet("Intruder");
            assertEquals(0, e.getMeetings().size(), "Executive should have zero meetings after rejection");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
