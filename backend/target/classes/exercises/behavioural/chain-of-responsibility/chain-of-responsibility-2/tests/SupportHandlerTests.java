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

    static SupportHandler buildChain() {
        L1Support l1 = new L1Support();
        L2Support l2 = new L2Support();
        L3Support l3 = new L3Support();
        l1.setNext(l2);
        l2.setNext(l3);
        return l1;
    }

    public static void main(String[] args) {
        test("L1 resolves severity-1 ticket", () -> {
            String result = buildChain().handle(new Ticket("Password reset", 1));
            assertTrue(result.contains("L1"), "L1 should handle severity 1");
        });

        test("L2 resolves severity-2 ticket", () -> {
            String result = buildChain().handle(new Ticket("Database error", 2));
            assertTrue(result.contains("L2"), "L2 should handle severity 2");
        });

        test("L3 resolves severity-3 ticket", () -> {
            String result = buildChain().handle(new Ticket("System outage", 3));
            assertTrue(result.contains("L3"), "L3 should handle severity 3");
        });

        test("result contains the ticket issue", () -> {
            String result = buildChain().handle(new Ticket("System outage", 3));
            assertTrue(result.contains("System outage"), "result should mention the ticket issue");
        });

        test("severity-1 ticket is not escalated to L2", () -> {
            String result = buildChain().handle(new Ticket("FAQ question", 1));
            assertTrue(!result.contains("L2") && !result.contains("L3"), "severity-1 should stay at L1");
        });

        test("severity-2 ticket bypasses L1 and is handled by L2", () -> {
            String result = buildChain().handle(new Ticket("Network issue", 2));
            assertTrue(!result.contains("L1"), "severity-2 should not be handled by L1");
            assertTrue(result.contains("L2"),  "severity-2 should be handled by L2");
        });

        test("chain without L3 throws for severity-3 ticket", () -> {
            L1Support l1 = new L1Support();
            L2Support l2 = new L2Support();
            l1.setNext(l2);
            boolean threw = false;
            try { l1.handle(new Ticket("Critical failure", 3)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "should throw when no handler covers the severity");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
