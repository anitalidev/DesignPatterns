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

    static ExpenseHandler buildChain() {
        Manager  manager  = new Manager();
        Director director = new Director();
        CEO      ceo      = new CEO();
        manager.setNext(director);
        director.setNext(ceo);
        return manager;
    }

    public static void main(String[] args) {
        test("Manager approves $100", () -> {
            String result = buildChain().approve(new Expense("Lunch", 100));
            assertTrue(result.contains("Manager"), "Manager should approve $100");
        });

        test("Manager approves exactly $500", () -> {
            String result = buildChain().approve(new Expense("Equipment", 500));
            assertTrue(result.contains("Manager"), "Manager should approve exactly $500");
        });

        test("Director approves $501", () -> {
            String result = buildChain().approve(new Expense("Conference", 501));
            assertTrue(result.contains("Director"), "Director should approve $501");
        });

        test("Director approves exactly $5000", () -> {
            String result = buildChain().approve(new Expense("Server", 5000));
            assertTrue(result.contains("Director"), "Director should approve exactly $5000");
        });

        test("CEO approves $5001", () -> {
            String result = buildChain().approve(new Expense("Office", 5001));
            assertTrue(result.contains("CEO"), "CEO should approve $5001");
        });

        test("CEO approves very large amounts", () -> {
            String result = buildChain().approve(new Expense("Acquisition", 1_000_000));
            assertTrue(result.contains("CEO"), "CEO should approve any large amount");
        });

        test("approval result contains the expense description", () -> {
            String result = buildChain().approve(new Expense("Team dinner", 200));
            assertTrue(result.contains("Team dinner"), "result should mention the expense description");
        });

        test("chain without CEO throws for amount > Director limit", () -> {
            Manager  manager  = new Manager();
            Director director = new Director();
            manager.setNext(director);
            boolean threw = false;
            try { manager.approve(new Expense("Big purchase", 9999)); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "should throw when no handler covers the amount");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
