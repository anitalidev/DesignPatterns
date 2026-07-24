class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("CreditCardStrategy.pay() mentions Credit Card", () -> {
            String result = new CreditCardStrategy().pay(50.0);
            assertTrue(result.toLowerCase().contains("credit"), "expected result to contain \"credit\" — got: \"" + result + "\"");
        });
        test("CreditCardStrategy.pay() includes the amount", () -> {
            String result = new CreditCardStrategy().pay(99.99);
            assertTrue(result.contains("99.99"), "should contain the amount — got: \"" + result + "\"");
        });
        test("PayPalStrategy.pay() mentions PayPal", () -> {
            String result = new PayPalStrategy().pay(50.0);
            assertTrue(result.toLowerCase().contains("paypal"), "should mention PayPal — got: \"" + result + "\"");
        });
        test("PayPalStrategy.pay() includes the amount", () -> {
            String result = new PayPalStrategy().pay(42.0);
            assertTrue(result.contains("42"), "PayPal result should include the amount — got: \"" + result + "\"");
        });
        test("CryptoStrategy.pay() mentions Crypto", () -> {
            String result = new CryptoStrategy().pay(50.0);
            assertTrue(result.toLowerCase().contains("crypto"), "should mention Crypto — got: \"" + result + "\"");
        });
        test("CryptoStrategy.pay() includes the amount", () -> {
            String result = new CryptoStrategy().pay(7.5);
            assertTrue(result.contains("7.5"), "Crypto result should include the amount — got: \"" + result + "\"");
        });
        test("PaymentProcessor delegates to CreditCard strategy", () -> {
            String result = new PaymentProcessor(new CreditCardStrategy()).pay(100.0);
            assertTrue(result.toLowerCase().contains("credit"), "processor should use CreditCard — got: \"" + result + "\"");
        });
        test("PaymentProcessor.pay() result includes the amount", () -> {
            String result = new PaymentProcessor(new CreditCardStrategy()).pay(55.0);
            assertTrue(result.contains("55"), "processor result should include amount — got: \"" + result + "\"");
        });
        test("setStrategy() swaps to PayPal", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new PayPalStrategy());
            String result = p.pay(50.0);
            assertTrue(result.toLowerCase().contains("paypal"), "should use PayPal after swap — got: \"" + result + "\"");
        });
        test("setStrategy() swaps to Crypto", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new CryptoStrategy());
            String result = p.pay(50.0);
            assertTrue(result.toLowerCase().contains("crypto"), "should use Crypto after swap — got: \"" + result + "\"");
        });
        test("each strategy is assignable to PaymentStrategy", () -> {
            PaymentStrategy cc = new CreditCardStrategy(); PaymentStrategy pp = new PayPalStrategy(); PaymentStrategy cr = new CryptoStrategy();
            assertTrue(cc != null && pp != null && cr != null, "all should be assignable to PaymentStrategy");
        });
        test("swapping strategy does not affect a second processor", () -> {
            PaymentProcessor p1 = new PaymentProcessor(new CreditCardStrategy());
            PaymentProcessor p2 = new PaymentProcessor(new CreditCardStrategy());
            p1.setStrategy(new PayPalStrategy());
            String result = p2.pay(10.0);
            assertTrue(result.toLowerCase().contains("credit"), "p2 should still use CreditCard — got: \"" + result + "\"");
        });

        // Balance tests
        test("CreditCardStrategy starts with balance 1000.0", () -> {
            CreditCardStrategy s = new CreditCardStrategy();
            assertTrue(s.getBalance() == 1000.0, "CreditCard initial balance should be 1000.0 — got: " + s.getBalance());
        });
        test("PayPalStrategy starts with balance 500.0", () -> {
            PayPalStrategy s = new PayPalStrategy();
            assertTrue(s.getBalance() == 500.0, "PayPal initial balance should be 500.0 — got: " + s.getBalance());
        });
        test("CryptoStrategy starts with balance 250.0", () -> {
            CryptoStrategy s = new CryptoStrategy();
            assertTrue(s.getBalance() == 250.0, "Crypto initial balance should be 250.0 — got: " + s.getBalance());
        });
        test("CreditCardStrategy.pay() deducts from balance", () -> {
            CreditCardStrategy s = new CreditCardStrategy();
            s.pay(100.0);
            assertTrue(s.getBalance() == 900.0, "balance should be 900.0 after paying 100.0 — got: " + s.getBalance());
        });
        test("PayPalStrategy.pay() deducts from balance", () -> {
            PayPalStrategy s = new PayPalStrategy();
            s.pay(50.0);
            assertTrue(s.getBalance() == 450.0, "balance should be 450.0 after paying 50.0 — got: " + s.getBalance());
        });
        test("CryptoStrategy.pay() deducts from balance", () -> {
            CryptoStrategy s = new CryptoStrategy();
            s.pay(25.0);
            assertTrue(s.getBalance() == 225.0, "balance should be 225.0 after paying 25.0 — got: " + s.getBalance());
        });
        test("multiple payments accumulate deductions", () -> {
            CreditCardStrategy s = new CreditCardStrategy();
            s.pay(100.0); s.pay(200.0); s.pay(50.0);
            assertTrue(s.getBalance() == 650.0, "balance should be 650.0 after three payments totalling 350.0 — got: " + s.getBalance());
        });
        test("each strategy instance has its own independent balance", () -> {
            CreditCardStrategy s1 = new CreditCardStrategy();
            CreditCardStrategy s2 = new CreditCardStrategy();
            s1.pay(500.0);
            assertTrue(s2.getBalance() == 1000.0, "s2 balance should be unaffected by s1 payment — got: " + s2.getBalance());
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
