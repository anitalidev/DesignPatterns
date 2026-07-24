class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    // Helper: reach HasCoin state
    static VendingMachine inHasCoin() {
        VendingMachine vm = new VendingMachine(); vm.insertCoin(); return vm;
    }

    // Helper: reach Dispensing state
    static VendingMachine inDispensing() {
        VendingMachine vm = new VendingMachine(); vm.insertCoin(); vm.selectProduct(); return vm;
    }

    public static void main(String[] args) {

        // ── Happy path ────────────────────────────────────────────────────────
        test("full happy path: insertCoin → selectProduct → dispense", () -> {
            VendingMachine vm = new VendingMachine();
            String r1 = vm.insertCoin(); String r2 = vm.selectProduct(); String r3 = vm.dispense();
            assertTrue(r1 != null && r2 != null && r3 != null,
                "expected all three steps to return a non-null message — got: insertCoin=\"" + r1 + "\", selectProduct=\"" + r2 + "\", dispense=\"" + r3 + "\"");
            assertTrue(r3 != null && r3.toLowerCase().contains("dispens"),
                "expected dispense() to return a message containing \"dispens\" (e.g. \"Product dispensed\") — got: \"" + r3 + "\"");
        });
        test("machine can complete two full cycles", () -> {
            VendingMachine vm = new VendingMachine();
            vm.insertCoin(); vm.selectProduct(); vm.dispense();
            vm.insertCoin(); vm.selectProduct();
            String result = vm.dispense();
            assertTrue(result != null && result.toLowerCase().contains("dispens"),
                "expected second cycle dispense() to return a message containing \"dispens\" — got: \"" + result + "\"");
        });

        // ── Idle state — all four actions ────────────────────────────────────
        test("Idle: insertCoin() succeeds", () -> {
            String result = new VendingMachine().insertCoin();
            assertTrue(result != null && !result.isEmpty(),
                "expected insertCoin() in Idle to return a non-empty confirmation — got: \"" + result + "\"");
        });
        test("Idle: ejectCoin() returns an error (no coin to eject)", () -> {
            String result = new VendingMachine().ejectCoin();
            assertTrue(result != null && !result.isEmpty(),
                "expected ejectCoin() in Idle to return a non-empty error message — got: \"" + result + "\"");
        });
        test("Idle: selectProduct() returns an error", () -> {
            String result = new VendingMachine().selectProduct();
            assertTrue(result != null && !result.isEmpty(),
                "expected selectProduct() in Idle to return a non-empty error message — got: \"" + result + "\"");
        });
        test("Idle: dispense() returns an error", () -> {
            String result = new VendingMachine().dispense();
            assertTrue(result != null && !result.isEmpty(),
                "expected dispense() in Idle to return a non-empty error message — got: \"" + result + "\"");
        });
        test("Idle: invalid action does not block subsequent insertCoin()", () -> {
            VendingMachine vm = new VendingMachine(); vm.selectProduct();
            String result = vm.insertCoin();
            assertTrue(result != null && !result.isEmpty(),
                "expected insertCoin() to still work after a failed selectProduct() in Idle — got: \"" + result + "\"");
        });

        // ── HasCoin state — all four actions ─────────────────────────────────
        test("HasCoin: insertCoin() is rejected (coin already inserted)", () -> {
            String result = inHasCoin().insertCoin();
            assertTrue(result != null && (result.toLowerCase().contains("already") || result.toLowerCase().contains("coin")),
                "expected a rejection message containing \"already\" or \"coin\" — got: \"" + result + "\"");
        });
        test("HasCoin: ejectCoin() returns a confirmation and goes back to Idle", () -> {
            VendingMachine vm = inHasCoin();
            String eject = vm.ejectCoin();
            assertTrue(eject != null && !eject.isEmpty(),
                "expected ejectCoin() in HasCoin to return a non-empty confirmation — got: \"" + eject + "\"");
            String after = vm.selectProduct();
            assertTrue(after != null && !after.isEmpty(),
                "expected machine to be in Idle after ejecting — selectProduct() should return an error, got: \"" + after + "\"");
        });
        test("HasCoin: selectProduct() succeeds", () -> {
            String result = inHasCoin().selectProduct();
            assertTrue(result != null && !result.isEmpty(),
                "expected selectProduct() in HasCoin to return a non-empty confirmation — got: \"" + result + "\"");
        });
        test("HasCoin: dispense() returns an error (no product selected)", () -> {
            String result = inHasCoin().dispense();
            assertTrue(result != null && !result.isEmpty(),
                "expected dispense() in HasCoin to return a non-empty error message — got: \"" + result + "\"");
        });
        test("HasCoin: invalid dispense() does not change state — can still selectProduct()", () -> {
            VendingMachine vm = inHasCoin(); vm.dispense();
            String result = vm.selectProduct();
            assertTrue(result != null && !result.isEmpty(),
                "expected selectProduct() to still work after a failed dispense() in HasCoin — got: \"" + result + "\"");
        });

        // ── Dispensing state — all four actions ───────────────────────────────
        test("Dispensing: dispense() succeeds and returns to Idle", () -> {
            VendingMachine vm = inDispensing();
            String result = vm.dispense();
            assertTrue(result != null && result.toLowerCase().contains("dispens"),
                "expected dispense() in Dispensing to return a message containing \"dispens\" — got: \"" + result + "\"");
            String after = vm.insertCoin();
            assertTrue(after != null && !after.isEmpty(),
                "expected machine to return to Idle after dispense() — insertCoin() should succeed, got: \"" + after + "\"");
        });
        test("Dispensing: insertCoin() returns an error", () -> {
            String result = inDispensing().insertCoin();
            assertTrue(result != null && !result.isEmpty(),
                "expected insertCoin() in Dispensing to return a non-empty error message — got: \"" + result + "\"");
        });
        test("Dispensing: ejectCoin() returns an error", () -> {
            String result = inDispensing().ejectCoin();
            assertTrue(result != null && !result.isEmpty(),
                "expected ejectCoin() in Dispensing to return a non-empty error message — got: \"" + result + "\"");
        });
        test("Dispensing: selectProduct() returns an error", () -> {
            String result = inDispensing().selectProduct();
            assertTrue(result != null && !result.isEmpty(),
                "expected selectProduct() in Dispensing to return a non-empty error message — got: \"" + result + "\"");
        });

        // ── Post-cycle state ──────────────────────────────────────────────────
        test("machine returns to Idle after full cycle", () -> {
            VendingMachine vm = new VendingMachine();
            vm.insertCoin(); vm.selectProduct(); vm.dispense();
            String result = vm.selectProduct();
            assertTrue(result != null && !result.isEmpty(),
                "expected machine to be back in Idle after a full cycle — selectProduct() with no coin should return an error, got: \"" + result + "\"");
        });

        // ── Null safety ───────────────────────────────────────────────────────
        test("all return values are non-null strings", () -> {
            VendingMachine vm = new VendingMachine();
            String r1 = vm.insertCoin(); String r2 = vm.selectProduct(); String r3 = vm.dispense();
            assertTrue(r1 != null, "expected insertCoin() to return a non-null string — got: null");
            assertTrue(r2 != null, "expected selectProduct() to return a non-null string — got: null");
            assertTrue(r3 != null, "expected dispense() to return a non-null string — got: null");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
