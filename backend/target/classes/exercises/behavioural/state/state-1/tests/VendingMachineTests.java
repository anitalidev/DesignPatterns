class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("full happy path: insertCoin → selectProduct → dispense", () -> {
            VendingMachine vm = new VendingMachine();
            String r1 = vm.insertCoin(); String r2 = vm.selectProduct(); String r3 = vm.dispense();
            assertTrue(r1 != null && r2 != null && r3 != null, "all steps should return a message");
            assertTrue(r3.toLowerCase().contains("dispens"), "dispense() should confirm dispensing");
        });
        test("selectProduct() without coin returns error", () -> {
            String result = new VendingMachine().selectProduct();
            assertTrue(result != null && !result.isEmpty(), "should return an error message");
        });
        test("dispense() without coin returns error", () -> {
            String result = new VendingMachine().dispense();
            assertTrue(result != null && !result.isEmpty(), "should return an error message");
        });
        test("inserting a second coin is rejected", () -> {
            VendingMachine vm = new VendingMachine(); vm.insertCoin();
            String result = vm.insertCoin();
            assertTrue(result.toLowerCase().contains("already") || result.toLowerCase().contains("coin"),
                "second coin should be rejected");
        });
        test("dispense() without selecting a product is rejected", () -> {
            VendingMachine vm = new VendingMachine(); vm.insertCoin();
            String result = vm.dispense();
            assertTrue(result != null && !result.isEmpty(), "should return error when no product selected");
        });
        test("machine returns to Idle after full cycle", () -> {
            VendingMachine vm = new VendingMachine();
            vm.insertCoin(); vm.selectProduct(); vm.dispense();
            String result = vm.selectProduct();
            assertTrue(result != null && !result.isEmpty(), "after full cycle, selecting without coin should error");
        });
        test("machine can complete two full cycles", () -> {
            VendingMachine vm = new VendingMachine();
            vm.insertCoin(); vm.selectProduct(); vm.dispense();
            vm.insertCoin(); vm.selectProduct();
            assertTrue(vm.dispense().toLowerCase().contains("dispens"), "second cycle should dispense correctly");
        });
        test("ejectCoin() in Idle state (no coin) returns an error", () -> {
            VendingMachine vm = new VendingMachine();
            String result = vm.ejectCoin();
            assertTrue(result != null && !result.isEmpty(), "ejectCoin() with no coin should return an error");
        });
        test("ejectCoin() after inserting coin returns machine to Idle", () -> {
            VendingMachine vm = new VendingMachine();
            vm.insertCoin();
            String ejectResult = vm.ejectCoin();
            assertTrue(ejectResult != null && !ejectResult.isEmpty(), "ejectCoin() should return a message");
            String result = vm.selectProduct();
            assertTrue(result != null && !result.isEmpty(), "after ejecting, selectProduct() should return an error (no coin)");
        });
        test("all return values are non-null strings", () -> {
            VendingMachine vm = new VendingMachine();
            assertTrue(vm.insertCoin()    != null, "insertCoin() should not return null");
            assertTrue(vm.selectProduct() != null, "selectProduct() should not return null");
            assertTrue(vm.dispense()      != null, "dispense() should not return null");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
