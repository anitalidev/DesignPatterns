class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    static SupportHandler buildChain() {
        L1Support l1 = new L1Support(); L2Support l2 = new L2Support(); L3Support l3 = new L3Support();
        l1.setNext(l2); l2.setNext(l3); return l1;
    }

    public static void main(String[] args) {
        test("L1 resolves severity-1 ticket", () -> {
            String result = buildChain().handle(new Ticket("Password reset", 1));
            assertTrue(result.contains("L1"), "L1 should handle severity 1 — got: \"" + result + "\"");
        });
        test("L2 resolves severity-2 ticket", () -> {
            String result = buildChain().handle(new Ticket("Database error", 2));
            assertTrue(result.contains("L2"), "L2 should handle severity 2 — got: \"" + result + "\"");
        });
        test("L3 resolves severity-3 ticket", () -> {
            String result = buildChain().handle(new Ticket("System outage", 3));
            assertTrue(result.contains("L3"), "L3 should handle severity 3 — got: \"" + result + "\"");
        });
        test("result contains the ticket issue", () -> {
            String result = buildChain().handle(new Ticket("System outage", 3));
            assertTrue(result.contains("System outage"), "result should mention ticket issue — got: \"" + result + "\"");
        });
        test("severity-1 ticket is not escalated to L2 or L3", () -> {
            String r = buildChain().handle(new Ticket("FAQ question", 1));
            assertTrue(!r.contains("L2") && !r.contains("L3"), "severity-1 should stay at L1 — got: \"" + r + "\"");
        });
        test("severity-2 ticket bypasses L1 and is handled by L2", () -> {
            String r = buildChain().handle(new Ticket("Network issue", 2));
            assertTrue(!r.contains("L1"), "severity-2 should not be handled by L1 — got: \"" + r + "\"");
            assertTrue(r.contains("L2"),  "severity-2 should be handled by L2 — got: \"" + r + "\"");
        });
        test("severity-3 ticket bypasses L1 and L2", () -> {
            String r = buildChain().handle(new Ticket("Outage", 3));
            assertTrue(!r.contains("L1") && !r.contains("L2"), "severity-3 should not be handled by L1 or L2 — got: \"" + r + "\"");
        });
        test("chain without L3 throws for severity-3 ticket", () -> {
            L1Support l1 = new L1Support(); L2Support l2 = new L2Support(); l1.setNext(l2);
            boolean threw = false;
            try { l1.handle(new Ticket("Critical failure", 3)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "should throw when no handler covers the severity");
        });
        test("severity beyond any handler throws", () -> {
            boolean threw = false;
            try { buildChain().handle(new Ticket("Unknown", 99)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "severity 99 should throw as no handler covers it");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
