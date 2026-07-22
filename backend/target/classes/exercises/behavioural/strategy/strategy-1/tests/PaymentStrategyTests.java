class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("CreditCardStrategy.pay() mentions Credit Card", () ->
            assertTrue(new CreditCardStrategy().pay(50.0).toLowerCase().contains("credit"), "should mention Credit Card"));
        test("CreditCardStrategy.pay() includes the amount", () ->
            assertTrue(new CreditCardStrategy().pay(99.99).contains("99.99"), "should contain the amount"));
        test("PayPalStrategy.pay() mentions PayPal", () ->
            assertTrue(new PayPalStrategy().pay(50.0).toLowerCase().contains("paypal"), "should mention PayPal"));
        test("PayPalStrategy.pay() includes the amount", () ->
            assertTrue(new PayPalStrategy().pay(42.0).contains("42"), "PayPal result should include the amount"));
        test("CryptoStrategy.pay() mentions Crypto", () ->
            assertTrue(new CryptoStrategy().pay(50.0).toLowerCase().contains("crypto"), "should mention Crypto"));
        test("CryptoStrategy.pay() includes the amount", () ->
            assertTrue(new CryptoStrategy().pay(7.5).contains("7.5"), "Crypto result should include the amount"));
        test("PaymentProcessor delegates to CreditCard strategy", () ->
            assertTrue(new PaymentProcessor(new CreditCardStrategy()).pay(100.0).toLowerCase().contains("credit"),
                "processor should use CreditCard"));
        test("PaymentProcessor.pay() result includes the amount", () ->
            assertTrue(new PaymentProcessor(new CreditCardStrategy()).pay(55.0).contains("55"),
                "processor result should include amount"));
        test("setStrategy() swaps to PayPal", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new PayPalStrategy());
            assertTrue(p.pay(50.0).toLowerCase().contains("paypal"), "should use PayPal after swap");
        });
        test("setStrategy() swaps to Crypto", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new CryptoStrategy());
            assertTrue(p.pay(50.0).toLowerCase().contains("crypto"), "should use Crypto after swap");
        });
        test("each strategy is assignable to PaymentStrategy", () -> {
            PaymentStrategy cc = new CreditCardStrategy(); PaymentStrategy pp = new PayPalStrategy(); PaymentStrategy cr = new CryptoStrategy();
            assertTrue(cc != null && pp != null && cr != null, "all should be assignable to PaymentStrategy");
        });
        test("swapping strategy does not affect a second processor", () -> {
            PaymentProcessor p1 = new PaymentProcessor(new CreditCardStrategy());
            PaymentProcessor p2 = new PaymentProcessor(new CreditCardStrategy());
            p1.setStrategy(new PayPalStrategy());
            assertTrue(p2.pay(10.0).toLowerCase().contains("credit"), "p2 should still use CreditCard");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
