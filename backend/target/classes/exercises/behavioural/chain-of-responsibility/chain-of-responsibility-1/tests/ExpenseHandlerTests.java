class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    static ExpenseHandler buildChain() {
        Manager m = new Manager(); Director d = new Director(); CEO ceo = new CEO();
        m.setNext(d); d.setNext(ceo); return m;
    }

    public static void main(String[] args) {
        test("Manager approves $100", () ->
            assertTrue(buildChain().approve(new Expense("Lunch", 100)).contains("Manager"), "Manager should approve $100"));
        test("Manager approves exactly $500", () ->
            assertTrue(buildChain().approve(new Expense("Equipment", 500)).contains("Manager"), "Manager should approve exactly $500"));
        test("Director approves $501", () ->
            assertTrue(buildChain().approve(new Expense("Conference", 501)).contains("Director"), "Director should approve $501"));
        test("Director approves exactly $5000", () ->
            assertTrue(buildChain().approve(new Expense("Server", 5000)).contains("Director"), "Director should approve exactly $5000"));
        test("CEO approves $5001", () ->
            assertTrue(buildChain().approve(new Expense("Office", 5001)).contains("CEO"), "CEO should approve $5001"));
        test("CEO approves very large amounts", () ->
            assertTrue(buildChain().approve(new Expense("Acquisition", 1_000_000)).contains("CEO"), "CEO should approve any large amount"));
        test("approval result contains the expense description", () ->
            assertTrue(buildChain().approve(new Expense("Team dinner", 200)).contains("Team dinner"), "result should mention expense description"));
        test("Manager does not approve above its limit", () -> {
            String result = buildChain().approve(new Expense("Big", 501));
            assertTrue(!result.contains("Manager"), "Manager should not approve $501");
        });
        test("Director does not approve above its limit", () -> {
            String result = buildChain().approve(new Expense("Huge", 5001));
            assertTrue(!result.contains("Director"), "Director should not approve $5001");
        });
        test("chain without CEO throws for amount above Director limit", () -> {
            Manager m = new Manager(); Director d = new Director(); m.setNext(d);
            boolean threw = false;
            try { m.approve(new Expense("Big", 9999)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "should throw when no handler covers the amount");
        });
        test("standalone Manager throws for amount above its limit", () -> {
            boolean threw = false;
            try { new Manager().approve(new Expense("Pricey", 600)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "standalone Manager should throw for amount > 500");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
